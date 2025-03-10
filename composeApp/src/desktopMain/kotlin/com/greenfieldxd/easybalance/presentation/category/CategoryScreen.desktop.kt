package com.greenfieldxd.easybalance.presentation.category

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.greenfieldxd.easybalance.presentation.AppColors
import com.greenfieldxd.easybalance.presentation.CustomButton

@Composable
actual fun CategorySection(
    screenModel: CategoryScreenModel,
    scrollState: LazyListState,
) {
    val categories by screenModel.categories.collectAsState(emptyList())
    val scrollBarVisible by remember {
        derivedStateOf {
            val canScroll = scrollState.canScrollForward || scrollState.canScrollBackward
            canScroll
        }
    }

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Box (modifier = Modifier.fillMaxSize()) {
            VerticalScrollbar(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight().padding(top = 56.dp),
                adapter = rememberScrollbarAdapter(scrollState)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = if (scrollBarVisible) 16.dp else 0.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Категории",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.OnBackground
                    )
                    CustomButton(
                        text = "Сбросить категории",
                        backgroundColor = AppColors.Background,
                        contentColor = AppColors.OnBackground,
                        onClick = { screenModel.returnToDefault() }
                    )
                }

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
    }
}