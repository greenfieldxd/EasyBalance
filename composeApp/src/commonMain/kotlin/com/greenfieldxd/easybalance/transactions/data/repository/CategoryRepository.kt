package com.greenfieldxd.easybalance.transactions.data.repository

interface CategoryRepository {
    fun getCategory(description: String): String
}

class CategoryRepositoryImpl : CategoryRepository {
    override fun getCategory(description: String): String {
        val lowerDesc = description.lowercase()

        val category = CategoryDataSource.categories.entries.firstOrNull { (_, keywords) ->
            keywords.any { lowerDesc.contains(it) }
        }?.key ?: "Другое"

        return category
    }
}