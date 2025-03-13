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
import com.greenfieldxd.easybalance.domain.TransactionFilterType
import com.greenfieldxd.easybalance.presentation.other.AppColors
import com.greenfieldxd.easybalance.presentation.other.CustomButton

actual class AnalyticsScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<AnalyticsScreenModel>()

        val transactionFilterType by screenModel.transactionFilterType.collectAsState(TransactionFilterType.TODAY)
        val expensesByCategory by screenModel.expensesByCategory.collectAsState(emptyList())

        Column (modifier = Modifier.fillMaxSize()) {
            Row (
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Аналитика",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.OnBackground
                )

                val filterButtonText = when (transactionFilterType) {
                    TransactionFilterType.TODAY -> "За сегодня"
                    TransactionFilterType.WEEK -> "За неделю"
                    TransactionFilterType.MONTH -> "За месяц"
                    TransactionFilterType.YEAR -> "За год"
                    TransactionFilterType.ALL_TIME -> "За все время"
                }

                CustomButton(
                    modifier = Modifier,
                    backgroundColor = AppColors.Background,
                    contentColor = AppColors.OnBackground,
                    text = filterButtonText,
                    onClick = { screenModel.nextFilterType() }
                )
            }

            if (expensesByCategory.size >= 2) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    CategoryPieChart(
                        modifier = Modifier.weight(0.75f),
                        expensesByCategory = expensesByCategory
                    )
                    ExpenseCategoryList(
                        modifier = Modifier.weight(0.25f).padding(horizontal = 16.dp).padding(bottom = 16.dp),
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