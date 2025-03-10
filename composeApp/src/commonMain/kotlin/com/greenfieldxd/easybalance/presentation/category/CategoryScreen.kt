package com.greenfieldxd.easybalance.presentation.category

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.greenfieldxd.easybalance.data.repository.CategoryData
import com.greenfieldxd.easybalance.domain.CategoryModel
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomButton
import com.greenfieldxd.easybalance.presentation.CustomTextField

@Composable
fun CategoryScreen(screenModel: CategoryScreenModel) {
    val scrollState = rememberLazyListState()

    CategorySection(screenModel, scrollState)
}

@Composable
expect fun CategorySection(screenModel: CategoryScreenModel, scrollState: LazyListState)

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: CategoryModel,
    onDelete: (id:Long) -> Unit,
    onSave: (id: Long, data: CategoryData) -> Unit
) {
    var extended by rememberSaveable { mutableStateOf(false) }
    var categoryInput by rememberSaveable { mutableStateOf(category.name) }
    var keywordsInput by rememberSaveable { mutableStateOf(category.keywords.joinToString(", ")) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AppColors.Surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = category.name,
                color = category.color,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            CustomButton(
                modifier = Modifier.width(128.dp),
                backgroundColor = AppColors.Surface,
                contentColor = AppColors.OnSurface,
                text = if (extended) "Скрыть" else "Изменить",
                textSize = 12.sp,
                onClick = { extended = !extended }
            )
        }

        Text(
            text = category.keywords.joinToString(", "),
            color = AppColors.OnSurface,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (extended) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    placeholder = "Категория",
                    value = categoryInput,
                    onValueChange = { categoryInput = it }
                )
                CustomTextField(
                    placeholder = "Ключевые слова",
                    value = keywordsInput,
                    onValueChange = { keywordsInput = it }
                )
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    CustomButton(
                        modifier = Modifier.weight(0.5f),
                        backgroundColor = AppColors.Primary,
                        text = "Сохранить",
                        textSize = 12.sp,
                        onClick = {
                            extended = false
                            val data = CategoryData(
                                name = categoryInput,
                                keywords = keywordsInput
                                    .split(", ")
                                    .map { it.trim() }
                                    .filter { it.isNotEmpty() },
                                color = category.color.toArgb()
                            )
                            onSave.invoke(category.id, data)
                        }
                    )
                    CustomButton(
                        modifier = Modifier.weight(0.5f),
                        backgroundColor = AppColors.Red,
                        text = "Удалить",
                        textSize = 12.sp,
                        onClick = { onDelete.invoke(category.id) }
                    )
                }
            }
        }
    }
}