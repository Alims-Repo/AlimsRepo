package com.alim.alimsrepo.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import io.github.alimsrepo.navease.generated.NavEaseHost
import io.github.alimsrepo.navease.runtime.presentation.NavTransition

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavEaseHost(   // ← generated overload, zero arguments needed
            onExitRequest = { /* show exit dialog or finish() */ },
            enableSharedTransitions = true,
            navTransition = NavTransition.Push,
        )
    }
}