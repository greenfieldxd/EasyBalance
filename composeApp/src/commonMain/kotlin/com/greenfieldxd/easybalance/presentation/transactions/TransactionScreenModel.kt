package com.greenfieldxd.easybalance.presentation.transactions

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.domain.TransactionClassifierUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TransactionScreenModel(
    private val transactionClassifierUseCase: TransactionClassifierUseCase,
    private val transactionDao: TransactionDao
) : ScreenModel {

    val transactions = transactionDao.getAll()
    val balance = transactionDao.getAll().map { transactionModels ->
        transactionModels.sumOf { it.count }
    }

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