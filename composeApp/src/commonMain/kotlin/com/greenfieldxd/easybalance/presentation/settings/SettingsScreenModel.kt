package com.greenfieldxd.easybalance.presentation.settings

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.greenfieldxd.easybalance.data.database.CategoryDao
import com.greenfieldxd.easybalance.data.database.TransactionDao
import com.greenfieldxd.easybalance.data.repository.CategoryRepository
import kotlinx.coroutines.launch

class SettingsScreenModel(
    private val categoryRepository: CategoryRepository,
    private val transactionDao: TransactionDao,
    private val categoryDao: CategoryDao
) : ScreenModel {

    fun deleteAllTransactions() {
        screenModelScope.launch {
            transactionDao.deleteAll()
        }
    }

    fun returnCategoriesToDefault() {
        screenModelScope.launch {
            categoryDao.deleteAll()
            categoryRepository.trySetDefaultCategories()
        }
    }
}