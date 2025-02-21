package com.greenfieldxd.easybalance.domain

import com.greenfieldxd.easybalance.data.TransactionType

data class TransactionModel(
    val id: Long,
    val count: Double,
    val category: String,
    val description: String,
    val date: String,
    val transactionType: TransactionType
)