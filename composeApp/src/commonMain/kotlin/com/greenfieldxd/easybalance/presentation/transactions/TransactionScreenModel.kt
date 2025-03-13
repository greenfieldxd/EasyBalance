package com.greenfieldxd.easybalance.presentation.transactions

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.domain.usecase.TransactionClassifierUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class TransactionScreenModel(
    private val transactionClassifierUseCase: TransactionClassifierUseCase,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : ScreenModel {

    val transactions = transactionDao.getAll().map {
        transactions -> transactions.sortedByDescending {
            LocalDateTime.parse(it.date)
        }
    }

    val balance = transactionDao.getAll().map { transactionModels ->
        transactionModels.sumOf {
            if (it.transactionType == TransactionType.INCOME) it.amount else -it.amount
        }
    }

    val categoriesData = categoryDao.getAll().map { categories ->
        categories.associate { category -> category.name to Pair(category.name, category.color) }
    }


    fun classifier(input: String, transactionType: TransactionType) {
        screenModelScope.launch {
            transactionClassifierUseCase.processTransaction(input, transactionType)
        }
    }

    fun deleteTransaction(id: Long) {
        screenModelScope.launch {
            transactionDao.delete(id)
        }
    }
}