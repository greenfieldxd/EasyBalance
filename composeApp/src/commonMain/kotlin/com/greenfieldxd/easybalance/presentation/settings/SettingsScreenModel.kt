package com.greenfieldxd.easybalance.presentation.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.database.TransactionDao
import kotlinx.coroutines.launch

class SettingsScreenModel(
    private val transactionDao: TransactionDao
) : ScreenModel {

    fun deleteAllTransactions() {
        screenModelScope.launch {
            transactionDao.deleteAll()
        }
    }
}