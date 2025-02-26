package com.greenfieldxd.easybalance.presentation.transactions

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.utils.formatNumber
import com.greenfieldxd.easybalance.domain.TransactionModel
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomButton
import com.greenfieldxd.easybalance.presentation.CustomTextField
import com.greenfieldxd.easybalance.presentation.CustomToggleButton
import com.greenfieldxd.easybalance.presentation.analytics.AnalyticsScreen
import kotlinx.coroutines.launch

class TransitionScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<TransactionScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        val transactions by screenModel.transactions.collectAsState(emptyList())
        val totalBalance by screenModel.balance.collectAsState(0.0)

        val scrollState = rememberLazyListState()
        var input by remember { mutableStateOf("") }
        var transactionType by remember { mutableStateOf(TransactionType.SPEND) }

        var previousSize by remember { mutableIntStateOf(0) }

        LaunchedEffect (transactions) {
            if (transactions.isNotEmpty() && transactions.size > previousSize){
                previousSize = transactions.size
                scrollState.animateScrollToItem(0)
            }
            else previousSize = transactions.size
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background)
                .padding(16.dp)
        ) {
            HeaderSection(navigator)
            Spacer(modifier = Modifier.height(16.dp))
            BalanceSection(
                balance = totalBalance,
                transactionType = transactionType,
                onTypeChange = { transactionType = it },
                input = input,
                onInputChange = { input = it },
                onAddClick = {
                    screenModel.classifier(input, transactionType)
                    input = ""
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransactionsListSection(transactions, scrollState, onEdit = { }, onDelete = { screenModel.deleteTransaction(it) })
        }
    }
}

@Composable
fun HeaderSection(navigator: Navigator) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Easy Balance",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.OnBackground
        )
        CustomButton(
            text = "Аналитика",
            onClick = { navigator.push(AnalyticsScreen()) }
        )
    }
}

@Composable
fun BalanceSection(
    balance: Double,
    input: String,
    transactionType: TransactionType,
    onTypeChange: (TransactionType) -> Unit,
    onInputChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val transactionText = when (transactionType) {
        TransactionType.INCOME -> "Доход"
        TransactionType.SPEND -> "Расход"
    }

    val targetColor = when (transactionType) {
        TransactionType.INCOME -> AppColors.Green
        TransactionType.SPEND -> AppColors.Red
    }

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 300)
    )

    Column {
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = "${formatNumber(balance)} BYN",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.OnBackground
        )
        CustomButton(
            text = transactionText,
            backgroundColor = animatedColor,
            onClick = {
                if (transactionType == TransactionType.INCOME) onTypeChange.invoke(TransactionType.SPEND)
                else onTypeChange.invoke(TransactionType.INCOME)
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            placeholder = "Пример: Такси 12.10",
            value = input,
            onValueChange = onInputChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            text = "Добавить",
            onClick = onAddClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = input.isNotBlank()
        )
    }
}

@Composable
expect fun TransactionsListSection(transactions: List<TransactionModel>, scrollState: LazyListState, onEdit: () -> Unit, onDelete: (Long) -> Unit)

@Composable
expect fun TransactionItem(modifier: Modifier = Modifier, transaction: TransactionModel, onEdit: (() -> Unit)? = null, onDelete: ((Long) -> Unit)? = null)