package com.greenfieldxd.easybalance

import androidx.compose.ui.window.ComposeUIViewController
import com.greenfieldxd.easybalance.di.initKoin
import com.greenfieldxd.easybalance.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}