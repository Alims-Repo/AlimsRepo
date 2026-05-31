package com.alim.alimsrepo.core.data

import androidx.compose.ui.graphics.Color
import com.alim.alimsrepo.core.domain.model.Library

val allLibraries = listOf(
    Library(
        id = "navease",
        name = "NavEase",
        description = "Annotation-driven navigation for Compose Multiplatform. Zero boilerplate, typed arguments, shared element transitions.",
        version = "1.0.0-alpha",
        artifact = "io.github.alims-repo:navease-runtime",
        platforms = listOf("Android", "iOS", "Desktop", "Web"),
        accentColor = Color(0xFF58A6FF),
        githubUrl = "https://github.com/Alims-Repo/NavEase",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/navease-runtime",
        stars = 42
    ),
    Library(
        id = "flowtab",
        name = "FlowTab-CMP",
        description = "Beautiful animated bottom navigation for Compose Multiplatform. Glassmorphism, badges, and 60fps transitions.",
        version = "0.5.5-beta",
        artifact = "io.github.alims-repo:flowtab-cmp",
        platforms = listOf("Android", "iOS"),
        accentColor = Color(0xFFA371F7),
        githubUrl = "https://github.com/Alims-Repo/FlowTab-CMP",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/flowtab-cmp",
        stars = 10
    ),
    Library(
        id = "prayertimes",
        name = "Prayer Times KMM",
        description = "Accurate Islamic prayer time calculations. 11+ methods, madhab support, high-latitude handling.",
        version = "1.0.4-beta",
        artifact = "io.github.alims-repo:prayer-times-kmm",
        platforms = listOf("Android", "iOS", "JVM"),
        accentColor = Color(0xFF3FB950),
        githubUrl = "https://github.com/Alims-Repo/Prayer-Times-KMM",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/prayer-times-kmm",
        stars = 4
    ),
    Library(
        id = "crashguard",
        name = "Crash-Guard",
        description = "Industry-grade Android crash handling with customizable crash screens and persistent logging.",
        version = "1.0.2",
        artifact = "io.github.alims-repo:crash-guard",
        platforms = listOf("Android"),
        accentColor = Color(0xFFFF7B72),
        githubUrl = "https://github.com/Alims-Repo/Crash-Guard",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/crash-guard",
        stars = 3
    ),
    Library(
        id = "pdfgenerator",
        name = "Pdf-Generator",
        description = "Kotlin DSL for generating multi-page PDFs on Android. Tables, text, and automatic pagination.",
        version = "1.0.0",
        artifact = "io.github.alims-repo:pdf-generator",
        platforms = listOf("Android"),
        accentColor = Color(0xFFFFA657),
        githubUrl = "https://github.com/Alims-Repo/Pdf-Generator",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/pdf-generator",
        stars = 3
    ),
    Library(
        id = "quranapi",
        name = "Quran-API",
        description = "Easy access to Quran data with audio support. Built for Islamic apps.",
        version = "1.0.0-beta",
        artifact = "io.github.alims-repo:quran-api",
        platforms = listOf("Android"),
        accentColor = Color(0xFF2EA043),
        githubUrl = "https://github.com/Alims-Repo/Quran-API",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/quran-api",
        stars = 2
    )
)