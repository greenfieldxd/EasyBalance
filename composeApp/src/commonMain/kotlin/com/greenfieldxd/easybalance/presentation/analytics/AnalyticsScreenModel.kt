package com.greenfieldxd.easybalance.presentation.analytics

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.domain.TransactionFilterType
import com.greenfieldxd.easybalance.domain.model.PieChartModel
import com.greenfieldxd.easybalance.domain.usecase.GetAnalyticsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class AnalyticsScreenModel(
    private val getAnalyticsUseCase: GetAnalyticsUseCase
) : ScreenModel {

    private val _transactionFilterType = MutableStateFlow(TransactionFilterType.TODAY)
    val transactionFilterType = _transactionFilterType.asStateFlow()

    val expensesByCategory = _transactionFilterType.flatMapLatest { filterType ->
        getAnalyticsUseCase.getExpensesByCategory(filterType)
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
}