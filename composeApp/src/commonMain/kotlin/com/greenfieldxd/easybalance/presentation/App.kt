package com.greenfieldxd.easybalance.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.greenfieldxd.easybalance.presentation.transactions.TransitionScreen

@Composable
fun App(modifier: Modifier = Modifier) {
    MaterialTheme {
        Box (modifier = modifier){
            Navigator(TransitionScreen()) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}