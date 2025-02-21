package com.greenfieldxd.easybalance.domain

import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.data.repository.CategoryRepository
import com.greenfieldxd.easybalance.data.utils.todayDate

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
                transactionDao.insert(amount, category, description, todayDate(), transactionType.ordinal)
            }
        }
    }

    private fun extractAmountAndCategory(input: String): Triple<Double?, String, String> {
        val amountRegex = Regex("\\d+(\\.\\d+)?")
        val amount = amountRegex.find(input)?.value?.toDoubleOrNull()
        val inputWithoutAmount = input.replace(amountRegex, "").trim()
        val categoryInfo = categoryRepository.getCategory(inputWithoutAmount)

        return Triple(amount, categoryInfo.first, categoryInfo.second)
    }
}