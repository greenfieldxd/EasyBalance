package com.greenfieldxd.easybalance

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState(
        width = 400.dp,
        height = 400.dp
    )
    Window(
        state = windowState,
        onCloseRequest = ::exitApplication,
        title = "EasyBalance",
    ) {
        App()
    }
}