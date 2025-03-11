package com.greenfieldxd.easybalance.domain

import com.greenfieldxd.easybalance.data.TransactionType

data class TransactionModel(
    val id: Long,
    val amount: Double,
    val categoryId: Long,
    val description: String,
    val date: String,
    val transactionType: TransactionType
)