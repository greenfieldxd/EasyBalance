package com.greenfieldxd.easybalance.presentation.transactions

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.domain.model.TransactionModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EditTransitionScreenModel(
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : ScreenModel {

    private val _transactionState = MutableStateFlow<TransactionModel?>(null)
    val transactionState = _transactionState.asStateFlow()

    val categories = categoryDao.getAll()

    fun loadTransaction(id: Long) {
        screenModelScope.launch {
            val transaction = transactionDao.get(id)
            _transactionState.value = transaction
        }
    }

    fun updateTransaction(transactionModel: TransactionModel) {
        screenModelScope.launch {
            transactionDao.updateTransaction(transactionModel.id, transactionModel)
        }
    }
}