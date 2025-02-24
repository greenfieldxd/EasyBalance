package com.greenfieldxd.easybalance.presentation.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomButton
import com.hd.charts.PieChartView
import com.hd.charts.common.model.ChartDataSet
import com.hd.charts.style.ChartViewDefaults
import com.hd.charts.style.ChartViewStyle
import com.hd.charts.style.PieChartDefaults

class AnalyticsScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AnalyticsScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddCustomPieChart()
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                text = "Назад",
                onClick = { navigator.pop() }
            )
        }
    }
}

@Composable
private fun AddCustomPieChart() {
    val pieColors = listOf(
        AppColors.Primary,
        AppColors.PrimaryVariant,
        AppColors.Purple,
        AppColors.LightPurple,
        AppColors.DarkPurple,
        AppColors.Orange,
        AppColors.Brown,
        AppColors.Yellow
    )

    val style = PieChartDefaults.style(
        borderColor = Color.White,
        donutPercentage = 40f,
        borderWidth = 4f,
        pieColors = pieColors,
    )

    val dataSet = ChartDataSet(
        items = listOf(60.0f, 25f, 15f, 14f, 30f, 30f, 20f, 30f),
        title = "Chart",
        postfix = " °C"
    )

    PieChartView(dataSet = dataSet, style = style)
}