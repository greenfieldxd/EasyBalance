package com.greenfieldxd.easybalance.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.utils.formatToCurrency
import com.greenfieldxd.easybalance.domain.TransactionFilterType
import com.greenfieldxd.easybalance.domain.model.TransactionModel
import com.greenfieldxd.easybalance.presentation.other.AppColors
import com.greenfieldxd.easybalance.presentation.other.ChangeTransactionTypeButton
import com.greenfieldxd.easybalance.presentation.other.CustomButton
import com.greenfieldxd.easybalance.presentation.other.CustomTextField

class TransitionScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<TransactionScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        val transactions by screenModel.transactions.collectAsState(emptyList())
        val transactionFilterType by screenModel.transactionFilterType.collectAsState(TransactionFilterType.TODAY)
        val categoriesData by screenModel.categoriesData.collectAsState(emptyMap())
        val totalBalance by screenModel.balance.collectAsState(0.0)

        val scrollState = rememberLazyListState()
        var input by remember { mutableStateOf("") }
        var transactionType by remember { mutableStateOf(TransactionType.SPEND) }

        var previousSize by remember { mutableIntStateOf(0) }

        LaunchedEffect(transactions) {
            if (transactions.isNotEmpty() && transactions.size > previousSize) {
                previousSize = transactions.size
                scrollState.animateScrollToItem(0)
            } else previousSize = transactions.size
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.Background)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            BalanceSection(
                balance = formatToCurrency(amount = totalBalance),
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
            TransactionsListSection(
                scrollState = scrollState,
                transactions = transactions,
                transactionFilterType = transactionFilterType,
                categoriesData = categoriesData,
                onChangeTransactionsFilter = { screenModel.nextFilterType() },
                onEdit = { navigator.push(EditTransitionScreen(it)) },
                onDelete = { screenModel.deleteTransaction(it) })
        }
    }
}

@Composable
fun BalanceSection(
    balance: String,
    input: String,
    transactionType: TransactionType,
    onTypeChange: (TransactionType) -> Unit,
    onInputChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Column {
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = balance,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = AppColors.OnBackground
        )
        ChangeTransactionTypeButton(transactionType = transactionType, onTypeChange = onTypeChange)
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
expect fun TransactionsListSection(
    transactions: List<TransactionModel>,
    transactionFilterType: TransactionFilterType,
    categoriesData: Map<String, Pair<String, Color>>,
    scrollState: LazyListState,
    onChangeTransactionsFilter: (TransactionFilterType) -> Unit,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
)

@Composable
expect fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: TransactionModel,
    categoriesData: Map<String, Pair<String, Color>>,
    onEdit: ((Long) -> Unit)? = null,
    onDelete: ((Long) -> Unit)? = null
)