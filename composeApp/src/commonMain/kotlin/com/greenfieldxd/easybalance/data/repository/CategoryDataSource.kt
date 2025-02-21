package com.greenfieldxd.easybalance.data.repository

object CategoryDataSource {
    val categories = mapOf(
        "Продукты" to listOf("еда", "магазин", "продукты", "супермаркет"),
        "Транспорт" to listOf("метро", "автобус", "такси", "бензин"),
        "Развлечения" to listOf("кино", "игры", "путешествия", "концерт"),
        "Другое" to emptyList()
    )
}