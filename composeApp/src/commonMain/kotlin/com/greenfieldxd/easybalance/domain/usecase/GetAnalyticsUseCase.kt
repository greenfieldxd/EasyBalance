package com.greenfieldxd.easybalance.domain.usecase

import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.data.utils.todayDateTime
import com.greenfieldxd.easybalance.domain.TransactionFilterType
import com.greenfieldxd.easybalance.domain.model.PieChartModel
import com.greenfieldxd.easybalance.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus

interface GetAnalyticsUseCase {
    fun getExpensesByCategory(filterType: TransactionFilterType): Flow<List<PieChartModel>>
}

class GetAnalyticsUseCaseImpl(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : GetAnalyticsUseCase {

    override fun getExpensesByCategory(filterType: TransactionFilterType): Flow<List<PieChartModel>> {
        return combine(
            transactionDao.getAll(),
            categoryDao.getAll()
        ) { transactions, categories ->
            val filteredTransactions = filterTransactions(transactions, filterType)
                .filter { it.transactionType == TransactionType.SPEND }

            filteredTransactions.groupBy { it.category }
                .mapNotNull { (category, transList) ->
                    categories.find { it.name == category }?.let { categoryModel ->
                        val categoryTotal = transList.sumOf { it.amount }
                        PieChartModel(
                            category = categoryModel,
                            value = categoryTotal
                        )
                    }
                }
                .sortedByDescending { it.value }
        }
    }

    private fun filterTransactions(
        transactions: List<TransactionModel>,
        filterType: TransactionFilterType
    ): List<TransactionModel> {
        val currentDate = LocalDateTime.parse(todayDateTime())
        return when (filterType) {
            TransactionFilterType.TODAY -> transactions.filter { it.date.date == currentDate.date }
            TransactionFilterType.WEEK -> transactions.filter { it.date.isInCurrentWeek(currentDate) }
            TransactionFilterType.MONTH -> transactions.filter { it.date.month == currentDate.month && it.date.year == currentDate.year }
            TransactionFilterType.YEAR -> transactions.filter { it.date.year == currentDate.year }
            TransactionFilterType.ALL_TIME -> transactions
        }
    }

    private fun LocalDateTime.isInCurrentWeek(currentDate: LocalDateTime): Boolean {
        val startOfWeek = currentDate.date.minus(currentDate.dayOfWeek.ordinal.toLong(), DateTimeUnit.DAY)
        val endOfWeek = startOfWeek.plus(6, DateTimeUnit.DAY)
        return this.date in startOfWeek..endOfWeek
    }
}