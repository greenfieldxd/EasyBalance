package com.greenfieldxd.easybalance.presentation.analytics

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.database.TransactionDao
import kotlinx.coroutines.launch

class AnalyticsScreenModel(
    private val transactionDao: TransactionDao
) : ScreenModel {
    init {
        screenModelScope.launch {
            //init some
        }
    }

    //Optional
    override fun onDispose() {
        super.onDispose()
    }
}