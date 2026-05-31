package com.alim.alimsrepo.core.data

import androidx.compose.ui.graphics.Color
import com.alim.alimsrepo.core.domain.model.Library

val allLibraries = listOf(
    Library(
        id = "navease",
        name = "NavEase",
        description = "Annotation-driven navigation for Compose Multiplatform. Zero boilerplate, fully typed arguments via sealed classes, and built-in shared element transitions — all powered by KSP code generation.",
        version = "1.0.0-alpha",
        artifact = "io.github.alims-repo:navease-runtime",
        platforms = listOf("Android", "iOS", "Desktop", "Web"),
        accentColor = Color(0xFF58A6FF),
        githubUrl = "https://github.com/Alims-Repo/NavEase",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/navease-runtime",
        stars = 42,
        category = "Navigation",
        features = listOf(
            "Zero-boilerplate via @AutoRegister annotation",
            "Fully typed arguments using Kotlin sealed classes",
            "Built-in shared element transition support",
            "Per-navigate transition overrides",
            "Type-safe back result system",
            "KSP code generation — no runtime reflection"
        )
    ),
    Library(
        id = "flowtab",
        name = "FlowTab-CMP",
        description = "Beautiful animated bottom navigation for Compose Multiplatform. Features a glassmorphism design, badge support, and buttery-smooth 60fps transitions on both Android and iOS.",
        version = "0.5.5-beta",
        artifact = "io.github.alims-repo:flowtab-cmp",
        platforms = listOf("Android", "iOS"),
        accentColor = Color(0xFFA371F7),
        githubUrl = "https://github.com/Alims-Repo/FlowTab-CMP",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/flowtab-cmp",
        stars = 10,
        category = "UI Component",
        features = listOf(
            "Glassmorphism animated tab bar",
            "Badge support with numeric counters",
            "Smooth 60fps spring-based transitions",
            "Android & iOS Compose Multiplatform",
            "Fully customisable colours and shapes",
            "Minimal integration — just one composable"
        )
    ),
    Library(
        id = "prayertimes",
        name = "Prayer Times KMM",
        description = "Accurate Islamic prayer time calculations for Kotlin Multiplatform. Supports 11+ calculation methods, madhab variants, and robust high-latitude rules — works fully offline.",
        version = "1.0.4-beta",
        artifact = "io.github.alims-repo:prayer-times-kmm",
        platforms = listOf("Android", "iOS", "JVM"),
        accentColor = Color(0xFF3FB950),
        githubUrl = "https://github.com/Alims-Repo/Prayer-Times-KMM",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/prayer-times-kmm",
        stars = 4,
        category = "Islamic Tools",
        features = listOf(
            "11+ calculation methods (ISNA, MWL, Egypt…)",
            "Madhab support — Hanafi & Shafi",
            "High-latitude rule handling",
            "Works fully offline — pure Kotlin math",
            "Android, iOS & JVM (Kotlin Multiplatform)",
            "DST and timezone-aware calculations"
        )
    ),
    Library(
        id = "crashguard",
        name = "Crash-Guard",
        description = "Industry-grade Android crash handling with fully customisable crash screens and persistent local crash log storage. Simple single-call setup — just init and you're protected.",
        version = "1.0.2",
        artifact = "io.github.alims-repo:crash-guard",
        platforms = listOf("Android"),
        accentColor = Color(0xFFFF7B72),
        githubUrl = "https://github.com/Alims-Repo/Crash-Guard",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/crash-guard",
        stars = 3,
        category = "Crash Handling",
        features = listOf(
            "Custom branded crash screen",
            "Persistent local crash log storage",
            "Restart or close app on crash",
            "Thread-safe crash interception",
            "Single-line initialisation in Application",
            "Stacktrace capture and formatting"
        )
    ),
    Library(
        id = "pdfgenerator",
        name = "Pdf-Generator",
        description = "A Kotlin DSL for generating multi-page PDFs on Android. Declaratively build pages with tables, styled text, and automatic pagination — no third-party JVM dependencies.",
        version = "1.0.0",
        artifact = "io.github.alims-repo:pdf-generator",
        platforms = listOf("Android"),
        accentColor = Color(0xFFFFA657),
        githubUrl = "https://github.com/Alims-Repo/Pdf-Generator",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/pdf-generator",
        stars = 3,
        category = "Document",
        features = listOf(
            "Kotlin DSL — declarative page building",
            "Automatic multi-page pagination",
            "Table support with custom column widths",
            "Text styling: bold, italic, size, alignment",
            "Android-native, zero third-party JVM deps",
            "Export to file or byte stream"
        )
    ),
    Library(
        id = "quranapi",
        name = "Quran-API",
        description = "Easy access to complete Quran data with audio support. Designed specifically for Islamic apps — provides all 114 Surahs, Ayah metadata, Arabic text, and recitation audio URLs.",
        version = "1.0.0-beta",
        artifact = "io.github.alims-repo:quran-api",
        platforms = listOf("Android"),
        accentColor = Color(0xFF2EA043),
        githubUrl = "https://github.com/Alims-Repo/Quran-API",
        mavenUrl = "https://central.sonatype.com/artifact/io.github.alims-repo/quran-api",
        stars = 2,
        category = "Islamic Tools",
        features = listOf(
            "All 114 Surahs with complete Ayah data",
            "Arabic text with transliterations",
            "Audio URL support for recitations",
            "Surah & Ayah metadata (Meccan/Medinan)",
            "Lightweight — no server required",
            "Designed specifically for Islamic apps"
        )
    )
)

fun libraryById(id: String): Library? = allLibraries.find { it.id == id }
