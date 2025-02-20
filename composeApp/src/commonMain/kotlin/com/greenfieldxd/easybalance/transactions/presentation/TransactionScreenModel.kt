package com.greenfieldxd.easybalance.transactions.presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.transactions.data.repository.CategoryRepository
import kotlinx.coroutines.launch

class TransactionScreenModel(
    private val categoryRepository: CategoryRepository
) : ScreenModel {

    init {
        screenModelScope.launch {
            //Some init fun
        }
    }

    //Optional
    override fun onDispose() {
        super.onDispose()
    }
}