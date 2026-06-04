package com.alim.alimsrepo.app

import io.github.alimsrepo.navease.runtime.NavEaseRoot
import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens : NavEaseRoot {

    @Serializable
    data object Splash : AppScreens()

    @Serializable
    data object Main : AppScreens()

    @Serializable
    data class LibraryDetail(val libraryId: String) : AppScreens()
}