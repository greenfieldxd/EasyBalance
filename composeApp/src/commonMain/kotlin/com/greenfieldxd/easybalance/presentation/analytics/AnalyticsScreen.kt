package com.greenfieldxd.easybalance.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.greenfieldxd.easybalance.data.utils.formatToCurrency
import com.greenfieldxd.easybalance.domain.PieChartModel
import com.greenfieldxd.easybalance.presentation.AppColors
import io.github.dautovicharis.charts.PieChart
import io.github.dautovicharis.charts.model.toChartDataSet
import io.github.dautovicharis.charts.style.PieChartDefaults

expect class AnalyticsScreen() : Screen

@Composable
fun CategoryPieChart(modifier: Modifier = Modifier, expensesByCategory: List<PieChartModel>) {

    val density = LocalDensity.current
    val borderWidth = with(density) { 3.dp.toPx() }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val colors = expensesByCategory.map { it.category.color }
        val labels = expensesByCategory.map { it.category.name }
        val dataSet = expensesByCategory.map { it.value }
            .toChartDataSet(
                title = "Категории",
                labels = labels
            )

        PieChart(
            dataSet = dataSet,
            style = PieChartDefaults.style(
                pieColors = colors,
                borderColor = AppColors.Surface,
                borderWidth = borderWidth,
                donutPercentage = 20f
            )
        )
    }
}

@Composable
fun ExpenseCategoryList(modifier: Modifier = Modifier, expenses: List<PieChartModel>) {

    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expenses) { expense ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(expense.category.color, shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = expense.category.name, style = MaterialTheme.typography.labelMedium)
                    }
                    Text(text = formatToCurrency(expense.value), style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}