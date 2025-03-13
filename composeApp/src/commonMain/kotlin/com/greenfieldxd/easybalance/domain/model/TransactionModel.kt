package com.greenfieldxd.easybalance.domain.model

import com.greenfieldxd.easybalance.data.TransactionType

data class TransactionModel(
    val id: Long,
    val amount: Double,
    val category: String,
    val description: String,
    val date: String,
    val transactionType: TransactionType
)