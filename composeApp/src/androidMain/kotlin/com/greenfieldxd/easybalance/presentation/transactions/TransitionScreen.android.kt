package com.greenfieldxd.easybalance.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.utils.formatDate
import com.greenfieldxd.easybalance.data.utils.formatToCurrency
import com.greenfieldxd.easybalance.domain.TransactionFilterType
import com.greenfieldxd.easybalance.domain.model.TransactionModel
import com.greenfieldxd.easybalance.presentation.other.AppColors
import com.greenfieldxd.easybalance.presentation.other.CustomButton
import com.greenfieldxd.easybalance.presentation.other.CustomSwipeBox

@Composable
actual fun TransactionsListSection(
    transactions: List<TransactionModel>,
    transactionFilterType: TransactionFilterType,
    categoriesData: Map<String, Pair<String, Color>>,
    scrollState: LazyListState,
    onChangeTransactionsFilter: (TransactionFilterType) -> Unit,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Транзакции",
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
                onClick = { onChangeTransactionsFilter.invoke(transactionFilterType) }
            )
        }

        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = transactions,
                key = { it.id }
            ) { transaction ->
                CustomSwipeBox(modifier = Modifier.animateItem(), onEdit = { onEdit.invoke(transaction.id) }, onDelete = { onDelete.invoke(transaction.id) }) {
                    TransactionItem(transaction = transaction, categoriesData = categoriesData)
                }
            }
        }
    }
}

@Composable
actual fun TransactionItem(
    modifier: Modifier,
    transaction: TransactionModel,
    categoriesData: Map<String, Pair<String, Color>>,
    onEdit: ((Long) -> Unit)?,
    onDelete: ((Long) -> Unit)?)
{
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = formatToCurrency(transaction.amount),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (transaction.transactionType == TransactionType.INCOME) AppColors.Green else AppColors.Red
            )
            Text(
                text = categoriesData[transaction.category]?.first ?: "Разное",
                style = MaterialTheme.typography.labelLarge,
                color = AppColors.OnSurface
            )
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(categoriesData[transaction.category]?.second ?: AppColors.Primary, shape = CircleShape)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = formatDate(transaction.date),
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.OnSurface
            )
            Text(
                text = transaction.description,
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.OnSurface
            )
        }
    }
}