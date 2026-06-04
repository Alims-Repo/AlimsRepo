package com.alim.alimsrepo.app

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.alim.alimsrepo.core.ui.theme.AlimsRepoTheme
import io.github.alimsrepo.navease.runtime.host.NavEaseHost
import io.github.alimsrepo.navease.runtime.transition.NavTransition

@Composable
@Preview
fun App() {
    AlimsRepoTheme {
        NavEaseHost<AppScreens>(
            start = AppScreens.Splash,
            onExitRequest = { /* show exit dialog or finish() */ },
            enableSharedTransitions = true,
            navTransition = NavTransition.Push,
        )
    }
}