package com.greenfieldxd.easybalance.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.greenfieldxd.easybalance.data.repository.CategoryData
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CategoryColors
import com.greenfieldxd.easybalance.presentation.CustomButton
import kotlinx.coroutines.launch

@Composable
actual fun CategorySection(
    screenModel: CategoryScreenModel,
    scrollState: LazyListState,
) {
    val categories by screenModel.categories.collectAsState(emptyList())
    val categoryColors by screenModel.categoryColors.collectAsState(emptyList())
    var isCategoryCreated by remember { mutableStateOf(false) }

    LaunchedEffect(isCategoryCreated) {
        if (isCategoryCreated && categories.isNotEmpty()) {
            scrollState.animateScrollToItem(categories.lastIndex)
            isCategoryCreated = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Категории",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppColors.OnBackground
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomButton(
                modifier = Modifier.weight(1f),
                text = "Создать категорию",
                onClick = {
                    val data = CategoryData(
                        name = "Шаблон категории",
                        keywords = listOf("Ключевые слова"),
                        color = AppColors.Primary.toArgb()
                    )
                    screenModel.create(data)
                    isCategoryCreated = true
                }
            )
            CustomButton(
                modifier = Modifier.weight(1f),
                text = "Сбросить категории",
                backgroundColor = AppColors.Red,
                onClick = { screenModel.returnToDefault() }
            )
        }

        LazyColumn(
            state = scrollState,
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = categories,
                key = { it.id }
            ) { category ->
                CategoryItem(
                    modifier = Modifier.animateItem(),
                    category = category,
                    onDelete = { screenModel.delete(it) },
                    onSave = { id, data -> screenModel.update(id = id, categoryModel = data) }
                )
            }
        }
    }
}

@Composable
actual fun ColorPicker(
    initColor: Color,
    colors: List<Color>,
    onSelected: (Int) -> Unit
) {
    val state = rememberLazyListState()
    var selectedColor by remember { mutableIntStateOf(initColor.toArgb()) }

    LaunchedEffect(Unit) {
        val index = colors.indexOfFirst { it.toArgb() == selectedColor }
        if (index >= 0) state.scrollToItem(index)
    }

    LazyRow(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(CategoryColors.list) { color ->
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color = if (color.toArgb() == selectedColor) AppColors.Surface else color)
                    .clickable {
                        selectedColor = color.toArgb()
                        onSelected.invoke(selectedColor)
                    }
                    .border(
                        width = 5.dp,
                        color = if (color.toArgb() == selectedColor) color else Color.Transparent,
                        shape = CircleShape
                    )
            )
        }
    }
}