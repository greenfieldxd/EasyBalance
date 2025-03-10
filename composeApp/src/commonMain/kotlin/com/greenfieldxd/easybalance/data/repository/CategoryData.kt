package com.greenfieldxd.easybalance.data.repository

import kotlinx.serialization.Serializable

@Serializable
data class CategoryData(
    val name: String,
    val keywords: List<String>,
    val color: Int
)