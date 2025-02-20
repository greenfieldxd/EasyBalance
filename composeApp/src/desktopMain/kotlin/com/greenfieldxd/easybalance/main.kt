package com.greenfieldxd.easybalance

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.greenfieldxd.easybalance.transactions.di.initKoin
import com.greenfieldxd.easybalance.transactions.presentation.App

fun main() {
    initKoin()
    application {
        val windowState = rememberWindowState(
            width = 400.dp,
            height = 800.dp
        )
        Window(
            state = windowState,
            onCloseRequest = ::exitApplication,
            title = "EasyBalance",
        ) {
            App()
        }
    }
}