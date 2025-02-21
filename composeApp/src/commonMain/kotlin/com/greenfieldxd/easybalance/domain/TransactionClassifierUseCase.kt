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
        val (amount, category) = extractAmountAndCategory(input)
        when {
            amount != null -> {
                transactionDao.insert(amount, category, "Description", todayDate(), transactionType.ordinal)
            }
        }
    }

    private fun extractAmountAndCategory(input: String): Pair<Double?, String> {
        val amountRegex = Regex("\\d+(\\.\\d+)?")
        val amount = amountRegex.find(input)?.value?.toDoubleOrNull()
        val description = input.replace(amountRegex, "").trim()
        val category = categoryRepository.getCategory(description)

        return Pair(amount, category)
    }
}