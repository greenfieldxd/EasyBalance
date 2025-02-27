package com.greenfieldxd.easybalance.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.greenfieldxd.easybalance.data.TransactionType
import com.greenfieldxd.easybalance.domain.TransactionModel
import databases.Database
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class TransactionDao(
    private val db: Database,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val queries  = db.databaseQueries

    suspend fun insert(amount: Double, category: String, description: String, date: String, transactionType: Int) = withContext(ioDispatcher) {
        queries.insertTransaction(amount = amount, category = category, description = description, date = date, transactionType = transactionType.toLong())
    }

    fun getAll() = queries.getAllTransactions(mapper = { id, amount, category, description, date, transactionType ->
        TransactionModel(
            id = id,
            amount = amount,
            category = category,
            description= description,
            date = date,
            transactionType = if (transactionType == TransactionType.INCOME.ordinal.toLong()) TransactionType.INCOME else TransactionType.SPEND
        )
    }).asFlow().mapToList(ioDispatcher)

    suspend fun updateAmount(id: Long, amount: Double) = withContext(ioDispatcher) {
        queries.updateTransactionCount(id = id, amount = amount)
    }

    suspend fun delete(id: Long) = withContext(ioDispatcher) {
        queries.deleteTransaction(id = id)
    }

    suspend fun deleteAll()  = withContext(ioDispatcher) {
        queries.deleteAllTransactions()
    }
}