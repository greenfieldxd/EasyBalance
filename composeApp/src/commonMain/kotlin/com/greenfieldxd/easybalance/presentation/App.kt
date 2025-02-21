package com.greenfieldxd.easybalance.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.greenfieldxd.easybalance.presentation.transactions.TransitionScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(TransitionScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}