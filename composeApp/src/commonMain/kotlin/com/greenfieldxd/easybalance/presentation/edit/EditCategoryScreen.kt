package com.greenfieldxd.easybalance.presentation.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.greenfieldxd.easybalance.domain.CategoryModel
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomButton

class EditCategoryScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<EditCategoryScreenModel>()
        val navigator = LocalNavigator.currentOrThrow

        val categories by screenModel.categories.collectAsState(emptyList())

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Редактирование категорий",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            CustomGrid(
                modifier = Modifier.weight(0.7f),
                items = categories,
                columns = 3
            ) { category ->
                CustomButton(
                    modifier = Modifier.fillMaxWidth().aspectRatio(2.5f),
                    text = category.name,
                    backgroundColor = AppColors.Surface,
                    contentColor = AppColors.OnSurface,
                    onClick = { }
                )
            }
            CustomButton(
                modifier = Modifier,
                text = "Отмена",
                backgroundColor = AppColors.Red,
                onClick = { navigator.pop() }
            )
        }
    }
}

@Composable
fun CustomGrid(
    modifier: Modifier = Modifier,
    items: List<CategoryModel>,
    columns: Int,
    content: @Composable (CategoryModel) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items.chunked(columns)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                rowItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    ) {
                        content(item)
                    }
                }
                repeat(columns - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}