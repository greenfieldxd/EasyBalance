package com.greenfieldxd.easybalance.domain.model

import com.greenfieldxd.easybalance.data.TransactionType
import kotlinx.datetime.LocalDateTime

data class TransactionModel(
    val id: Long,
    val amount: Double,
    val category: String,
    val description: String,
    val date: LocalDateTime,
    val transactionType: TransactionType
)