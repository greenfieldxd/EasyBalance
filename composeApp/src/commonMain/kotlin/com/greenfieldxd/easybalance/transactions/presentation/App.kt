package com.greenfieldxd.easybalance.transactions.presentation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        TransitionScreen().Content()
    }
}