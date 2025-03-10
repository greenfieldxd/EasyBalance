package com.greenfieldxd.easybalance.presentation.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.greenfieldxd.easybalance.domain.CategoryModel
import com.greenfieldxd.easybalance.presentation.AppColors

@Composable
actual fun CategorySection(
    screenModel: CategoryScreenModel,
    scrollState: LazyListState,
    navigator: Navigator,
    categories: List<CategoryModel>
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Категории",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AppColors.OnBackground
        )
        LazyColumn (
            state = scrollState,
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ){
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