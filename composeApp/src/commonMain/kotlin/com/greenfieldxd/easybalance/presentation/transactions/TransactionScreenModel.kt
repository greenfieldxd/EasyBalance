package com.greenfieldxd.easybalance.presentation.transactions

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.domain.TransactionFilterType
import com.greenfieldxd.easybalance.domain.usecase.FilteredTransactionsUseCase
import com.greenfieldxd.easybalance.domain.usecase.TransactionClassifierUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TransactionScreenModel(
    private val transactionClassifierUseCase: TransactionClassifierUseCase,
    private val transactionFilteredTransactionsUseCase: FilteredTransactionsUseCase,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : ScreenModel {

    private val _transactionFilterType = MutableStateFlow(TransactionFilterType.TODAY)
    val transactionFilterType = _transactionFilterType.asStateFlow()

    val transactions = _transactionFilterType.flatMapLatest { filterType ->
        transactionFilteredTransactionsUseCase.get(filterType)
    }

    val balance = transactionDao.getAll().map { transactionModels ->
        transactionModels.sumOf {
            if (it.transactionType == TransactionType.INCOME) it.amount else -it.amount
        }
    }

    val categoriesData = categoryDao.getAll().map { categories ->
        categories.associate { category -> category.name to Pair(category.name, category.color) }
    }

    fun nextFilterType() {
        val next = when (_transactionFilterType.value) {
            TransactionFilterType.TODAY -> TransactionFilterType.WEEK
            TransactionFilterType.WEEK -> TransactionFilterType.MONTH
            TransactionFilterType.MONTH -> TransactionFilterType.YEAR
            TransactionFilterType.YEAR -> TransactionFilterType.ALL_TIME
            TransactionFilterType.ALL_TIME -> TransactionFilterType.TODAY
        }

        _transactionFilterType.update { next }
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