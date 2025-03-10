package com.greenfieldxd.easybalance.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.greenfieldxd.easybalance.presentation.navigation.AnalyticsTab
import com.greenfieldxd.easybalance.presentation.navigation.CategoryTab
import com.greenfieldxd.easybalance.presentation.navigation.SettingsTab
import com.greenfieldxd.easybalance.presentation.navigation.TransactionTab

@Composable
fun App() {
    MaterialTheme {
        TabNavigator(TransactionTab()) {
            Scaffold(
                content = { innerPadding ->
                    Box(
                        modifier = Modifier
                            .background(AppColors.Background)
                            .padding(innerPadding)
                    ) {
                        val tabNavigator = LocalTabNavigator.current
                        AnimatedContent(
                            targetState = tabNavigator.current,
                            transitionSpec = {
                                val initialIndex = initialState.nonComposableIndex
                                val targetIndex = targetState.nonComposableIndex
                                if (initialIndex < targetIndex) {
                                    slideInHorizontally { fullWidth -> fullWidth } + fadeIn() togetherWith
                                            slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut()
                                }
                                else {
                                    slideInHorizontally { fullWidth -> -fullWidth } + fadeIn() togetherWith
                                            slideOutHorizontally { fullWidth -> fullWidth } + fadeOut()
                                }
                            }
                        ) { tab ->
                            tab.Content()
                        }
                    }
                },
                bottomBar = {
                    BottomAppBar (
                        containerColor = AppColors.Surface,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TabNavigationItem(TransactionTab())
                            TabNavigationItem(AnalyticsTab())
                            TabNavigationItem(CategoryTab())
                            TabNavigationItem(SettingsTab())
                        }
                    }
                }
            )
        }
    }
}

val Tab.nonComposableIndex: Int
    get() = when (this) {
        is TransactionTab -> 0
        is AnalyticsTab -> 1
        is CategoryTab -> 2
        is SettingsTab -> 3
        else -> 0
    }


@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val isSelected = tabNavigator.current.options.index == tab.options.index

    NavigationBarItem(
        selected = isSelected,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let {
                Icon(painter = it, contentDescription = tab.options.title)
            } ?: run {
                Icon(Icons.Default.Warning, contentDescription = tab.options.title)
            }
        },
        label = { Text(tab.options.title) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = AppColors.OnPrimary,
            unselectedIconColor = AppColors.OnSurface,
            selectedTextColor = AppColors.OnSurface,
            unselectedTextColor = AppColors.OnSurface,
            indicatorColor = AppColors.Primary
        )
    )
}