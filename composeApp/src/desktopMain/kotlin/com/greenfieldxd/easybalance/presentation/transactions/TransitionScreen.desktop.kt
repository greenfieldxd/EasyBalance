package com.greenfieldxd.easybalance.presentation.transactions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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

@Composable
actual fun TransactionsListSection(
    transactions: List<TransactionModel>,
    categoriesData: Map<Long, Pair<String, Color>>,
    scrollState: LazyListState,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    val scrollBarVisible by remember {
        derivedStateOf {
            val canScroll = scrollState.canScrollForward || scrollState.canScrollBackward
            canScroll
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight().padding(top = 48.dp),
            adapter = rememberScrollbarAdapter(scrollState)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp)
                .padding(end = if (scrollBarVisible) 16.dp else 0.dp)
        ) {
            Text(
                text = "Транзакции",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = AppColors.OnBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = transactions,
                    key = { it.id }
                ) { transaction ->
                    TransactionItem(
                        modifier = Modifier.animateItem(),
                        transaction = transaction,
                        categoriesData = categoriesData,
                        onEdit = onEdit,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}

@Composable
actual fun TransactionItem(
    modifier: Modifier,
    transaction: TransactionModel,
    categoriesData: Map<Long, Pair<String, Color>>,
    onEdit: ((Long) -> Unit)?,
    onDelete: ((Long) -> Unit)?
) {
    var extended by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = formatToCurrency(transaction.amount),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (transaction.transactionType == TransactionType.INCOME) AppColors.Green else AppColors.Red
            )
            AnimatedVisibility(extended) {
                Row {
                    IconButton(onClick = { onEdit?.invoke(transaction.id) }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            tint = AppColors.Primary,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { onDelete?.invoke(transaction.id) }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            tint = AppColors.Red,
                            contentDescription = null
                        )
                    }
                }
            }
            IconButton(onClick = { extended = !extended }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = categoriesData[transaction.categoryId]?.first ?: "Разное",
            style = MaterialTheme.typography.labelLarge,
            color = categoriesData[transaction.categoryId]?.second ?: AppColors.Primary
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