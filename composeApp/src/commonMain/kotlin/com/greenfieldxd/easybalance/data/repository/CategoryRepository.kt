package com.greenfieldxd.easybalance.data.repository

interface CategoryRepository {
    fun getCategory(description: String): Pair<String, String>
}

class CategoryRepositoryImpl : CategoryRepository {
    override fun getCategory(description: String): Pair<String, String> {
        val normalizedDesc = description.lowercase()

        // Проверяем каждую категорию
        for (category in CategoryDataSource.categories) {
            // Сначала пробуем найти совпадение по имени категории
            if (fuzzyMatchWord(category.name.lowercase(), normalizedDesc)) {
                return category.name to category.name
            }
            // Затем перебираем ключевые слова
            for (keyword in category.keywords) {
                if (fuzzyMatchWord(keyword.lowercase(), normalizedDesc)) {
                    return category.name to keyword
                }
            }
        }
        return "Разное" to "Не определено"
    }

    // Функция fuzzyMatchWord разбивает текст на токены и проверяет каждое на приближенность к искомому слову
    private fun fuzzyMatchWord(word: String, text: String, threshold: Double = 0.3): Boolean {
        // Разбиваем текст на отдельные слова (токены)
        val tokens = text.split(Regex("[\\s,.;:!?]+"))
        for (token in tokens) {
            // Если токен точно равен искомому слову, сразу возвращаем true
            if (token.equals(word, ignoreCase = true)) {
                return true
            }
            // Вычисляем расстояние Левенштейна между токеном и словом
            val distance = levenshteinDistance(token.lowercase(), word.lowercase())
            // Вычисляем относительное соотношение ошибки
            val ratio = distance.toDouble() / word.length.toDouble()
            if (ratio <= threshold) {
                return true
            }
        }
        return false
    }

    // Реализация алгоритма Левенштейна для оценки похожести строк
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
                    dp[i - 1][j] + 1,    // удаление
                    dp[i][j - 1] + 1,    // вставка
                    dp[i - 1][j - 1] + cost  // замена
                )
            }
        }
        return dp[a.length][b.length]
    }
}