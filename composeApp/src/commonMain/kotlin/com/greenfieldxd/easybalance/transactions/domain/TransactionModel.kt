package com.greenfieldxd.easybalance.transactions.domain

import com.greenfieldxd.easybalance.transactions.data.TransactionType

data class TransactionModel(
    val id: Long,
    val count: Double,
    val category: String,
    val description: String,
    val date: String,
    val transactionType: TransactionType
)