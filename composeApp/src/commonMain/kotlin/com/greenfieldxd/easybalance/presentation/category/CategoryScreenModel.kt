package com.greenfieldxd.easybalance.presentation.category

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.repository.CategoryData
import com.greenfieldxd.easybalance.data.repository.CategoryRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CategoryScreenModel(
    private val categoryDao: CategoryDao,
    private val categoryRepository: CategoryRepository
) : ScreenModel {
    val categories = categoryDao.getAll()
    val categoryColors = categoryDao.getAll().map { categoryModels -> categoryModels.map { it.color } }

    fun create(categoryData: CategoryData) {
        screenModelScope.launch {
            categoryDao.insert(categoryData)
        }
    }

    fun update(id: Long, categoryModel: CategoryData) {
        screenModelScope.launch {
            categoryDao.update(id, categoryModel)
        }
    }

    fun delete(id: Long){
        screenModelScope.launch {
            categoryDao.delete(id)
        }
    }

    fun returnToDefault() {
        screenModelScope.launch {
            categoryDao.deleteAll()
            categoryRepository.trySetDefaultCategories()
        }
    }
}