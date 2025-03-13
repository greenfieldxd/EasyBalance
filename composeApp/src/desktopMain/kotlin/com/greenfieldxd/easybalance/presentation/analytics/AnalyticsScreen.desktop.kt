package com.greenfieldxd.easybalance.presentation.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.greenfieldxd.easybalance.presentation.other.AppColors

actual class AnalyticsScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AnalyticsScreenModel>()
        val expensesByCategory by screenModel.expensesByCategory.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                text = "Аналитика",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = AppColors.OnBackground
            )

            if (expensesByCategory.size >= 2) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CategoryPieChart(
                        modifier = Modifier.weight(0.6f),
                        expensesByCategory = expensesByCategory
                    )
                    ExpenseCategoryList(
                        modifier = Modifier.weight(0.4f).padding(end = 16.dp),
                        expenses = expensesByCategory
                    )
                }
            }
            else {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Не хватает данных",
                        color = AppColors.Red,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}