package com.greenfieldxd.easybalance.domain.model

import androidx.compose.ui.graphics.Color

data class CategoryModel(
    val id: Long,
    val name: String,
    val keywords: List<String>,
    val color: Color
)