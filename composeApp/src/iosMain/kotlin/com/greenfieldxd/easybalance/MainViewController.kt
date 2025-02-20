package com.greenfieldxd.easybalance

import androidx.compose.ui.window.ComposeUIViewController
import com.greenfieldxd.easybalance.transactions.di.initKoin
import com.greenfieldxd.easybalance.transactions.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}