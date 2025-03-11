package com.greenfieldxd.easybalance.presentation.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.greenfieldxd.easybalance.presentation.AppColors

@Composable
actual fun AnalyticsScreen(screenModel: AnalyticsScreenModel) {
    val expensesByCategory by screenModel.expensesByCategory.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            "Аналитика",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppColors.OnBackground
        )
        CategoryPieChart(
            modifier = Modifier.weight(0.75f),
            expensesByCategory = expensesByCategory
        )
        ExpenseCategoryList(
            modifier = Modifier.weight(0.25f),
            expenses = expensesByCategory
        )
    }
}