package com.alim.alimsrepo.core.domain.model

import androidx.compose.ui.graphics.Color

data class Library(
    val id: String,
    val name: String,
    val description: String,
    val version: String,
    val artifact: String,
    val platforms: List<String>,
    val accentColor: Color,
    val githubUrl: String,
    val mavenUrl: String,
    val stars: Int? = null,
    val features: List<String> = emptyList(),
    val category: String = "Library"
)