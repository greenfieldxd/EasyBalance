package com.greenfieldxd.easybalance.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideOrientation
import cafe.adriel.voyager.transitions.SlideTransition
import com.greenfieldxd.easybalance.presentation.transactions.TransitionScreen

class TransactionTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Транзакции"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Navigator(TransitionScreen()) { navigator ->
            SlideTransition(navigator, orientation = SlideOrientation.Vertical)
        }
    }
}