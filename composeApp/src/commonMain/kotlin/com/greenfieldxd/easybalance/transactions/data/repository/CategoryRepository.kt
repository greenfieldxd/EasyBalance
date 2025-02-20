package com.greenfieldxd.easybalance.transactions.data.repository

interface CategoryRepository {
    fun getCategory(description: String): Pair<String, String>
}

class CategoryRepositoryImpl : CategoryRepository {
    override fun getCategory(description: String): Pair<String, String> {
        val lowerDesc = description.lowercase()

        val category = CategoryDataSource.categories.entries.firstOrNull { (_, keywords) ->
            keywords.any { lowerDesc.contains(it) }
        }?.key ?: "Другое"

        val subcategory = CategoryDataSource.subcategories.entries.firstOrNull { (_, keywords) ->
            keywords.any { lowerDesc.contains(it) }
        }?.key ?: "Общее"

        return category to subcategory
    }
}