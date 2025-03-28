package com.greenfieldxd.easybalance.data.repository

import com.greenfieldxd.easybalance.data.database.CategoryDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

interface CategoryRepository {
    fun trySetDefaultCategories()
    suspend fun getCategory(description: String): Pair<String, String>
}

class CategoryRepositoryImpl(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    private val categoriesCount = categoryDao.getCount()
    private val categoriesFlow = categoryDao.getAll()

    init {
        trySetDefaultCategories()
    }

    override fun trySetDefaultCategories() {
        val count = runBlocking { categoriesCount.first() }
        if (count == 0L) {
            for (category in CategoryDefaultDataSource.categories) {
                categoryDao.insert(category)
            }
        }
    }

    override suspend fun getCategory(description: String): Pair<String, String> {
        val categories = categoriesFlow.first()
        val normalizedDesc = description.lowercase()

        return categories.firstNotNullOfOrNull { category ->
            when {
                fuzzyMatchWord(
                    category.name.lowercase(),
                    normalizedDesc
                ) -> category.name to "Неопределено"

                category.keywords.any { keyword ->
                    fuzzyMatchWord(
                        keyword.lowercase(),
                        normalizedDesc
                    )
                } -> {
                    category.name to category.keywords.first { keyword ->
                        fuzzyMatchWord(
                            keyword.lowercase(),
                            normalizedDesc
                        )
                    }
                }

                else -> null
            }
        } ?: (CategoryDefaultDataSource.categories.last().name to "Неопределено")
    }

    private fun fuzzyMatchWord(word: String, text: String, threshold: Double = 0.3): Boolean {
        val tokens = text.split(Regex("[\\s,.;:!?]+"))
        for (token in tokens) {
            if (token.equals(word, ignoreCase = true)) {
                return true
            }
            val distance = levenshteinDistance(token.lowercase(), word.lowercase())
            val ratio = distance.toDouble() / word.length.toDouble()
            if (ratio <= threshold) {
                return true
            }
        }
        return false
    }

    private fun levenshteinDistance(a: String, b: String): Int {
        if (a == b) return 0
        if (a.isEmpty()) return b.length
        if (b.isEmpty()) return a.length

        val dp = Array(a.length + 1) { IntArray(b.length + 1) }
        for (i in 0..a.length) {
            dp[i][0] = i
        }
        for (j in 0..b.length) {
            dp[0][j] = j
        }
        for (i in 1..a.length) {
            for (j in 1..b.length) {
                val cost = if (a[i - 1] == b[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost
                )
            }
        }
        return dp[a.length][b.length]
    }
}