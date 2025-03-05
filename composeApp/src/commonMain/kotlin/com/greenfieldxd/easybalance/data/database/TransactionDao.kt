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

    suspend fun get(id: Long): TransactionModel? = withContext(ioDispatcher) {
        val transactionEntity = queries.getTransaction(id = id).executeAsOneOrNull()
            ?: return@withContext null

        return@withContext TransactionModel(
                id = transactionEntity.id,
                amount = transactionEntity.amount,
                category = transactionEntity.category,
                description = transactionEntity.description,
                date = transactionEntity.date,
                transactionType = if (transactionEntity.transactionType == TransactionType.INCOME.ordinal.toLong()) TransactionType.INCOME else TransactionType.SPEND
        )
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

    suspend fun updateTransaction(id: Long, transaction: TransactionModel) = withContext(ioDispatcher) {
        queries.updateTransaction(
            id = id,
            amount = transaction.amount,
            category = transaction.category,
            description = transaction.description,
            transactionType = transaction.transactionType.ordinal.toLong())
    }

    suspend fun delete(id: Long) = withContext(ioDispatcher) {
        queries.deleteTransaction(id = id)
    }

    suspend fun deleteAll()  = withContext(ioDispatcher) {
        queries.deleteAllTransactions()
    }
}