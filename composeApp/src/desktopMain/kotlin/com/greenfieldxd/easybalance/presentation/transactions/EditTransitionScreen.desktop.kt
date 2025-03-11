package com.greenfieldxd.easybalance.presentation.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.greenfieldxd.easybalance.domain.CategoryModel
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomButton
import kotlinx.coroutines.launch

@Composable
actual fun CategoryPicker(
    initCategoryId: Long,
    categories: List<CategoryModel>,
    onSelected: (CategoryModel) -> Unit
) {
    val state = rememberLazyListState()
    var selectedCategory by remember { mutableStateOf(initCategoryId) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val index = categories.indexOfFirst { it.id == selectedCategory }
        if (index >= 0) state.scrollToItem(index)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                scope.launch {
                    val firstVisibleItemIndex = state.firstVisibleItemIndex
                    state.animateScrollToItem(maxOf(firstVisibleItemIndex - 1, 0))
                }
            },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }

        LazyRow(
            state = state,
            modifier = Modifier.padding(horizontal = 16.dp).weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                val selected = selectedCategory == category.id
                CustomButton(
                    modifier = Modifier,
                    text = category.name,
                    backgroundColor = if (selected) category.color else AppColors.LightGray,
                    onClick = {
                        selectedCategory = category.id
                        onSelected.invoke(category)
                    }
                )
            }
        }

        IconButton(
            onClick = {
                scope.launch {
                    val firstVisibleItemIndex = state.firstVisibleItemIndex
                    state.animateScrollToItem(
                        minOf(firstVisibleItemIndex + 1, categories.lastIndex)
                    )
                }
            },
            modifier = Modifier.size(36.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
    }
}