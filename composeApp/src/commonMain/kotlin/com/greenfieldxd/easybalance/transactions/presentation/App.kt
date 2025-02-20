package com.greenfieldxd.easybalance.transactions.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun App() {
    MaterialTheme {
        Navigator(screen = TransitionScreen())
    }
}