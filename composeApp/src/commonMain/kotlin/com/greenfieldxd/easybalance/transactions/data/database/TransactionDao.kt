package com.greenfieldxd.easybalance.transactions.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import databases.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class TransactionDao(private val db: Database) {
    private val queries  = db.databaseQueries

    fun insert(count: Double, category: String, subcategory: String, date: Int, transactionType: Int) {
        queries.insertTransaction(count = count, category = category, subcategory = subcategory, date = date.toLong(), transactionType = transactionType.toLong())
    }

    fun getAll() = queries.getAllTransactions().asFlow().mapToList(Dispatchers.IO)

    fun updateCount(id: Long, count: Double) {
        queries.updateCount(id = id, count = count)
    }

    fun delete(id: Long) {
        queries.delete(id = id)
    }

    fun deleteAll() = queries.deleteAll()
}