package com.greenfieldxd.easybalance.presentation.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import com.greenfieldxd.easybalance.data.utils.formatToCurrency
import com.greenfieldxd.easybalance.domain.AnalyticModel
import com.greenfieldxd.easybalance.presentation.AppColors
import io.github.dautovicharis.charts.PieChart
import io.github.dautovicharis.charts.model.toChartDataSet
import io.github.dautovicharis.charts.style.PieChartDefaults

@Composable
expect fun AnalyticsScreen(screenModel: AnalyticsScreenModel)

@Composable
fun CategoryPieChart(modifier: Modifier = Modifier, expensesByCategory: List<AnalyticModel>) {
    val style = PieChartDefaults.style(
        pieColor = AppColors.Primary,
        borderColor = AppColors.Surface,
        donutPercentage = 20f,
        borderWidth = 6f,
        legendVisible = true,
        pieColors = expensesByCategory.map { it.category.color },
    )

    val items = expensesByCategory.map { it.percentage }
    val dataSet = items
        .toChartDataSet(
            title = "Категории расходов",
            postfix = " %"
        )

    Box (
        modifier = modifier.fillMaxSize()
    ) {
        PieChart(dataSet = dataSet, style = style)
    }
}

@Composable
fun ExpenseCategoryList(modifier: Modifier = Modifier, expenses: List<AnalyticModel>) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
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
                    Text(text = formatToCurrency(expense.total), style = MaterialTheme.typography.labelSmall)
                }
            }
        }
}