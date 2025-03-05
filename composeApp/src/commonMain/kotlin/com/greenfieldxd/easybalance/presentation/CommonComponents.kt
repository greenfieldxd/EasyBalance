package com.greenfieldxd.easybalance.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.greenfieldxd.easybalance.data.TransactionType

@Composable
fun ChangeTransactionTypeButton(
    transactionType: TransactionType,
    onTypeChange: (TransactionType) -> Unit,
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

    CustomButton(
        text = transactionText,
        backgroundColor = animatedColor,
        onClick = {
            if (transactionType == TransactionType.INCOME) onTypeChange.invoke(TransactionType.SPEND)
            else onTypeChange.invoke(TransactionType.INCOME)
        },
        modifier = Modifier.fillMaxWidth()
    )
}