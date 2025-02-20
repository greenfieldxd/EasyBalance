package com.greenfieldxd.easybalance.transactions.domain

import com.greenfieldxd.easybalance.transactions.data.repository.CategoryRepository

class TransactionClassifierUseCase(
    private val categoryRepository: CategoryRepository
) {
    fun processTransaction(input: String) {
        val (amount, category, subCategory) = extractAmountAndCategory(input)
        if (amount != null) {
            //val transaction = TransactionEntity(
            //    id = 0,
            //    count = amount,
            //    category = categoryPair.first,
            //    subcategory = categoryPair.second,
            //    date = getCurrentDate(),
            //    transactionType = TransactionType.SPEND
            //)
            //transactionDao.insert(transaction)
        }
    }

    private fun extractAmountAndCategory(input: String): Triple<Double?, String, String> {
        val amountRegex = Regex("\\d+(\\.\\d+)?")
        val amount = amountRegex.find(input)?.value?.toDoubleOrNull()
        val description = input.replace(amountRegex, "").trim()
        val categoryPair = categoryRepository.getCategory(description)

        return Triple(amount, categoryPair.first, categoryPair.second)
    }
}