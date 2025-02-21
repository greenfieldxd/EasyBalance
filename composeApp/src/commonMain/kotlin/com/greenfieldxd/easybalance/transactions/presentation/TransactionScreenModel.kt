package com.greenfieldxd.easybalance.transactions.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.transactions.data.TransactionType
import com.greenfieldxd.easybalance.transactions.data.database.TransactionDao
import com.greenfieldxd.easybalance.transactions.domain.TransactionClassifierUseCase
import kotlinx.coroutines.launch

class TransactionScreenModel(
    private val transactionClassifierUseCase: TransactionClassifierUseCase,
    private val transactionDao: TransactionDao
) : ScreenModel {

    val transactions = transactionDao.getAll()

    init {
        screenModelScope.launch {
            //Some init fun
        }
    }

    fun classifier(input: String, transactionType: TransactionType) {
        screenModelScope.launch {
            transactionClassifierUseCase.processTransaction(input, transactionType)
        }
    }

    //Optional
    override fun onDispose() {
        super.onDispose()
    }
}