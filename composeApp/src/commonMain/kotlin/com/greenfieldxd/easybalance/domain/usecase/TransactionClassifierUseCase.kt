package com.greenfieldxd.easybalance.domain.usecase

import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.data.repository.CategoryRepository
import com.greenfieldxd.easybalance.data.utils.todayDateTime
import com.greenfieldxd.easybalance.data.utils.truncateToDecimals

interface TransactionClassifierUseCase {
    suspend fun processTransaction(input: String, transactionType: TransactionType)
}

class TransactionClassifierUseCaseImpl(
    private val transactionDao: TransactionDao,
    private val categoryRepository: CategoryRepository
) : TransactionClassifierUseCase {
    override suspend fun processTransaction(input: String, transactionType: TransactionType) {
        val (amount, category, description) = extractAmountAndCategory(input)
        when {
            amount != null -> {
                transactionDao.insert(amount, category, description, todayDateTime(), transactionType.ordinal)
            }
        }
    }

    private suspend fun extractAmountAndCategory(input: String): Triple<Double?, String, String> {
        val amountRegex = Regex("\\d+(\\.\\d+)?")
        val amount = truncateToDecimals(amountRegex.find(input)?.value?.toDoubleOrNull() ?: 0.0, 2)
        val inputWithoutAmount = input.replace(amountRegex, "").trim()
        val categoryInfo = categoryRepository.getCategory(inputWithoutAmount)

        return Triple(amount, categoryInfo.first, categoryInfo.second)
    }
}