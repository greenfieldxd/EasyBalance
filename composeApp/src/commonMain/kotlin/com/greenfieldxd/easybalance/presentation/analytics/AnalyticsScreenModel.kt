package com.greenfieldxd.easybalance.presentation.analytics

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.domain.model.PieChartModel
import com.greenfieldxd.easybalance.domain.usecase.GetAnalyticsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnalyticsScreenModel(
    private val getAnalyticsUseCase: GetAnalyticsUseCase
) : ScreenModel {
    private val _expensesByCategory = MutableStateFlow<List<PieChartModel>>(emptyList())
    val expensesByCategory = _expensesByCategory.asStateFlow()

    init {
        loadAnalytics()
    }

    private fun loadAnalytics() {
        screenModelScope.launch {
            _expensesByCategory.value = getAnalyticsUseCase.getExpensesByCategory()
        }
    }
}