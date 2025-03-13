package com.greenfieldxd.easybalance.domain.usecase

import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.data.utils.todayDateTime
import com.greenfieldxd.easybalance.domain.TransactionFilterType
import com.greenfieldxd.easybalance.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus

interface FilteredTransactionsUseCase {
    fun get(filterType: TransactionFilterType): Flow<List<TransactionModel>>
}

class FilteredTransactionsUseCaseImpl (
    private val transactionDao: TransactionDao
) : FilteredTransactionsUseCase {

    override fun get(filterType: TransactionFilterType): Flow<List<TransactionModel>> {
        val currentDate = LocalDateTime.parse(todayDateTime())
        return transactionDao.getAll().map { transactions ->
            when (filterType) {
                TransactionFilterType.TODAY -> transactions.filter { it.date.date == currentDate.date }
                TransactionFilterType.WEEK -> transactions.filter { it.date.isInCurrentWeek(currentDate) }
                TransactionFilterType.MONTH -> transactions.filter { it.date.month == currentDate.month && it.date.year == currentDate.year }
                TransactionFilterType.YEAR -> transactions.filter { it.date.year == currentDate.year }
                TransactionFilterType.ALL_TIME -> transactions
            }.sortedByDescending { it.date }
        }
    }

    private fun LocalDateTime.isInCurrentWeek(currentDate: LocalDateTime): Boolean {
        val startOfWeek = currentDate.date.minus(currentDate.dayOfWeek.ordinal.toLong(), DateTimeUnit.DAY)
        val endOfWeek = startOfWeek.plus(6, DateTimeUnit.DAY)
        return this.date in startOfWeek..endOfWeek
    }
}