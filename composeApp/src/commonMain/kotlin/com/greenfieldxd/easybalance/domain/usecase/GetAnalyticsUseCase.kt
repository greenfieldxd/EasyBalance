package com.greenfieldxd.easybalance.domain.usecase

import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.domain.model.PieChartModel
import kotlinx.coroutines.flow.first

interface GetAnalyticsUseCase {
    suspend fun getExpensesByCategory(): List<PieChartModel>
}

class GetAnalyticsUseCaseImpl(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : GetAnalyticsUseCase {
    override suspend fun getExpensesByCategory(): List<PieChartModel> {
        val transactions = transactionDao.getAll().first()
        val categories = categoryDao.getAll().first()

        if (transactions.isNotEmpty() && categories.isNotEmpty()) {
            val filteredTransactions = transactions.filter { it.transactionType == TRANSITION_TYPE }
            return filteredTransactions.groupBy { it.category }
                .mapNotNull { (category, transList) ->
                    categories.find { it.name == category }?.let { categoryModel ->
                        val categoryTotal = transList.sumOf { it.amount }
                        PieChartModel(
                            category = categoryModel,
                            value = categoryTotal,
                        )
                    }
                }.sortedByDescending { it.value }
        }
        return emptyList()
    }

    companion object {
        private val TRANSITION_TYPE = TransactionType.SPEND
    }
}
