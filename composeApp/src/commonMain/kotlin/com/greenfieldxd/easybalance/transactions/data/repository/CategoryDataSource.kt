package com.greenfieldxd.easybalance.transactions.data.repository

object CategoryDataSource {
    val categories = mapOf(
        "Продукты" to listOf("еда", "магазин", "продукты", "супермаркет"),
        "Транспорт" to listOf("метро", "автобус", "такси", "бензин"),
        "Развлечения" to listOf("кино", "игры", "путешествия", "концерт"),
        "Другое" to emptyList()
    )

    val subcategories = mapOf(
        "Фрукты и овощи" to listOf("яблоки", "бананы", "овощи"),
        "Фастфуд" to listOf("бургер", "шаурма", "макдональдс"),
        "Такси" to listOf("яндекс такси", "uber", "gett")
    )
}