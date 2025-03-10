package com.greenfieldxd.easybalance.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.greenfieldxd.easybalance.presentation.category.CategoryScreen
import com.greenfieldxd.easybalance.presentation.category.CategoryScreenModel

class CategoryTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Категории"
            val icon = rememberVectorPainter(Icons.Default.Menu)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<CategoryScreenModel>()
        CategoryScreen(screenModel)
    }
}