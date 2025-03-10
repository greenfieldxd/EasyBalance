package com.greenfieldxd.easybalance.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.data.utils.formatDate
import com.greenfieldxd.easybalance.data.utils.formatToCurrency
import com.greenfieldxd.easybalance.domain.TransactionModel
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomSwipeBox

@Composable
actual fun TransactionsListSection(
    transactions: List<TransactionModel>,
    categoriesColorMap: Map<String, Color>,
    scrollState: LazyListState,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Транзакции",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppColors.OnBackground
        )
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
                val categoryColor = categoriesColorMap[transaction.category] ?: AppColors.Primary
                CustomSwipeBox(modifier = Modifier.animateItem(), onEdit = { onEdit.invoke(transaction.id) }, onDelete = { onDelete.invoke(transaction.id) }) {
                    TransactionItem(transaction = transaction, categoryColor = categoryColor)
                }
            }
        }
    }
}

@Composable
actual fun TransactionItem(modifier: Modifier, transaction: TransactionModel, categoryColor: Color, onEdit: ((Long) -> Unit)?, onDelete: ((Long) -> Unit)?) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = formatToCurrency(transaction.amount),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (transaction.transactionType == TransactionType.INCOME) AppColors.Green else AppColors.Red
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(

            text = transaction.category,
            style = MaterialTheme.typography.labelLarge,
            color = categoryColor
        )
        Row(modifier = Modifier.fillMaxWidth().padding(end = 20.dp)) {
            Text(
                modifier = Modifier.weight(1f),
                text = transaction.description,
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.OnSurface
            )
            Text(
                text = formatDate(transaction.date),
                style = MaterialTheme.typography.labelMedium,
                color = AppColors.OnSurface
            )
        }
    }
}