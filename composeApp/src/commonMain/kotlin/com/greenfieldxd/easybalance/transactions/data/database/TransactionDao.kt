package com.greenfieldxd.easybalance.transactions.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.greenfieldxd.easybalance.transactions.data.TransactionType
import com.greenfieldxd.easybalance.transactions.domain.TransactionModel
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

    suspend fun insert(count: Double, category: String, description: String, date: String, transactionType: Int) = withContext(ioDispatcher) {
        queries.insertTransaction(count = count, category = category, description = description, date = date, transactionType = transactionType.toLong())
    }

    fun getAll() = queries.getAllTransactions(mapper = { id, count, category, description, date, transactionType ->
        TransactionModel(
            id = id,
            count = count,
            category = category,
            description= description,
            date = date,
            transactionType = if (transactionType == TransactionType.INCOME.ordinal.toLong()) TransactionType.INCOME else TransactionType.SPEND
        )
    }).asFlow().mapToList(Dispatchers.IO)

    suspend fun updateCount(id: Long, count: Double) = withContext(ioDispatcher) {
        queries.updateCount(id = id, count = count)
    }

    suspend fun delete(id: Long) = withContext(ioDispatcher) {
        queries.delete(id = id)
    }

    suspend fun deleteAll()  = withContext(ioDispatcher) { queries.deleteAll() }
}