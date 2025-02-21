package com.greenfieldxd.easybalance.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.domain.TransactionModel
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomButton
import com.greenfieldxd.easybalance.presentation.CustomTextField
import com.greenfieldxd.easybalance.presentation.analytics.AnalyticsScreen

class TransitionScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<TransactionScreenModel>()
        val navigator = LocalNavigator.currentOrThrow
        val transactions by screenModel.transactions.collectAsState(emptyList())
        var balance by remember { mutableStateOf(0L) }
        var input by remember { mutableStateOf("") }
        var transactionType by remember { mutableStateOf(TransactionType.SPEND) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.weight(0.1f)) {
                Row (modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Easy Balance",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.OnBackground
                    )
                    CustomButton(
                        text = "Analytics",
                        onClick = { navigator.push(AnalyticsScreen()) },
                    )
                }
            }
            Column(modifier = Modifier.weight(0.2f)) {
                Text(
                    text = "$balance",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    placeholder = "Example: Food 125.10$",
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomButton(
                    text = "Add Transaction",
                    onClick = {
                        screenModel.classifier(input, transactionType)
                        balance += input.filter { it.isDigit() }.toLongOrNull() ?: 0
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = Modifier.weight(0.7f)) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Transactions",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.OnBackground
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(transactions) { transaction ->
                        TransactionItem(transaction)
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Surface, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = transaction.category,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = AppColors.OnSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${transaction.count}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (transaction.transactionType == TransactionType.INCOME) AppColors.Green else AppColors.SecondaryVariant
        )
    }
}