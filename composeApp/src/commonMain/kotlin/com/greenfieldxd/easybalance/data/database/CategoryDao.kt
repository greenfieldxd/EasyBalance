package com.greenfieldxd.easybalance.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.greenfieldxd.easybalance.data.repository.CategoryData
import com.greenfieldxd.easybalance.domain.CategoryModel
import databases.Database
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class CategoryDao(
    private val db: Database,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val queries  = db.databaseQueries
    private val json = Json { encodeDefaults = true }

    fun insert(category: CategoryData) {
        val jsonString = json.encodeToString(category)
        queries.insertCategory(jsonString)
    }

    fun getAll() = queries.getAllCategories(mapper = { id, data ->
        val categoryData = json.decodeFromString<CategoryData>(data)
        CategoryModel(
            id = id,
            name = categoryData.name,
            keywords = categoryData.keywords
        )
    }).asFlow().mapToList(ioDispatcher)

    suspend fun update(id: Long, category: CategoryModel) = withContext(ioDispatcher) {
        val jsonString = json.encodeToString(category)
        queries.updateCategoryData(id = id, data = jsonString)
    }

    suspend fun delete(id: Long) = withContext(ioDispatcher) {
        queries.deleteCategory(id = id)
    }

    suspend fun deleteAll()  = withContext(ioDispatcher) {
        queries.deleteAllCategories()
    }
}