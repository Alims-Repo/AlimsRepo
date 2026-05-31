package com.alim.alimsrepo.app

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreens : NavKey {

    @Serializable
    data object Splash : AppScreens()

    @Serializable
    data object Main : AppScreens()

    @Serializable
    data class LibraryDetail(val libraryId: String) : AppScreens()
}