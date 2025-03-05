package com.greenfieldxd.easybalance.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.greenfieldxd.easybalance.presentation.settings.SettingsScreen
import com.greenfieldxd.easybalance.presentation.settings.SettingsScreenModel

class SettingsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Настройки"
            val icon = rememberVectorPainter(Icons.Default.Settings)

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
        val screenModel = koinScreenModel<SettingsScreenModel>()
        SettingsScreen(screenModel)
    }
}