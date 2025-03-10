package com.greenfieldxd.easybalance.presentation.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.greenfieldxd.easybalance.domain.CategoryModel
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomButton

@Composable
actual fun CategoryPicker(
    initCategory: String,
    categories: List<CategoryModel>,
    onSelected: (CategoryModel) -> Unit
) {
    val state = rememberLazyListState()
    var selectedCategory by remember { mutableStateOf(initCategory) }

    LaunchedEffect(Unit) {
        val index = categories.indexOfFirst { it.name == selectedCategory }
        if (index >= 0) state.scrollToItem(index)
    }

    LazyRow (
        state = state,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            val selected = selectedCategory == category.name
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                text = category.name,
                backgroundColor = if (selected) category.color else AppColors.LightGray,
                onClick = {
                    selectedCategory = category.name
                    onSelected.invoke(category)
                }
            )
        }
    }
}