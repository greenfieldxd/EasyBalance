package com.greenfieldxd.easybalance.presentation.category

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.repository.CategoryData
import com.greenfieldxd.easybalance.domain.CategoryModel
import kotlinx.coroutines.launch

class CategoryScreenModel(
    private val categoryDao: CategoryDao
) : ScreenModel {
    val categories = categoryDao.getAll()

    fun create(categoryData: CategoryData) {
        screenModelScope.launch {
            categoryDao.insert(categoryData)
        }
    }

    fun update(id: Long, categoryModel: CategoryModel) {
        screenModelScope.launch {
            categoryDao.update(id, categoryModel)
        }
    }

    fun delete(id: Long){
        screenModelScope.launch {
            categoryDao.delete(id)
        }
    }

    fun deleteAll() {
        screenModelScope.launch {
            categoryDao.deleteAll()
        }
    }
}