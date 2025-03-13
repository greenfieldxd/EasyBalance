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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
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
import com.greenfieldxd.easybalance.domain.TransactionFilterType
import com.greenfieldxd.easybalance.domain.model.TransactionModel
import com.greenfieldxd.easybalance.presentation.other.AppColors
import com.greenfieldxd.easybalance.presentation.other.CustomButton

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
                .fillMaxHeight().padding(top = 70.dp),
            adapter = rememberScrollbarAdapter(scrollState)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp)
                .padding(end = if (scrollBarVisible) 16.dp else 0.dp)
        ) {
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
    categoriesData: Map<String, Pair<String, Color>>,
    onEdit: ((Long) -> Unit)?,
    onDelete: ((Long) -> Unit)?
) {
    var extended by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(7f),
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
                        .background(
                            color = categoriesData[transaction.category]?.second ?: AppColors.Primary,
                            shape = CircleShape
                        )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(),
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

        Row {
            AnimatedVisibility(visible = extended) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
    }
}