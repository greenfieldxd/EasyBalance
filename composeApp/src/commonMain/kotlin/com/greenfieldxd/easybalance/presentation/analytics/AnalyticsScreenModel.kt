package com.greenfieldxd.easybalance.presentation.analytics

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.domain.AnalyticModel
import com.greenfieldxd.easybalance.domain.GetAnalyticsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnalyticsScreenModel(
    private val getAnalyticsUseCase: GetAnalyticsUseCase
) : ScreenModel {
    private val _expensesByCategory = MutableStateFlow<List<AnalyticModel>>(emptyList())
    val expensesByCategory = _expensesByCategory.asStateFlow()

    private var _transactionType = TransactionType.SPEND

    init {
        loadAnalytics()
    }

    fun updateTransitionType(transactionType: TransactionType) {
        _transactionType = transactionType
        loadAnalytics()
    }

    private fun loadAnalytics() {
        screenModelScope.launch {
            _expensesByCategory.value = getAnalyticsUseCase.getExpensesByCategory(_transactionType)
        }
    }
}