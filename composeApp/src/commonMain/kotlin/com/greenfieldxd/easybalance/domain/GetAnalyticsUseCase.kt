package com.greenfieldxd.easybalance.domain

import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.database.TransactionDao
import kotlinx.coroutines.flow.first

interface GetAnalyticsUseCase {
    suspend fun getExpensesByCategory(transactionType: TransactionType): List<AnalyticModel>
}

class GetAnalyticsUseCaseImpl(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : GetAnalyticsUseCase {
    override suspend fun getExpensesByCategory(transactionType: TransactionType): List<AnalyticModel> {
        val transactions = transactionDao.getAll().first()
        val categories = categoryDao.getAll().first()

        if (transactions.isNotEmpty() && categories.isNotEmpty()) {
            val filteredTransactions = transactions.filter { it.transactionType == transactionType }
            val overallTotal = filteredTransactions.sumOf { it.amount }

            return filteredTransactions.groupBy { it.categoryId }
                .mapNotNull { (categoryId, transList) ->
                    categories.find { it.id == categoryId }?.let { category ->
                        val categoryTotal = transList.sumOf { it.amount }
                        val percentage = if (overallTotal > 0) (categoryTotal / overallTotal * 100).toFloat() else 0f
                        AnalyticModel(
                            category = category,
                            total = categoryTotal,
                            percentage = percentage
                        )
                    }
                }.sortedByDescending { it.total }
        }
        return emptyList()
    }
}
