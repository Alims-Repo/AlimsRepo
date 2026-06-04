package com.alim.alimsrepo.core.data

import androidx.compose.ui.graphics.Color

// ─── Developer Profile ────────────────────────────────────────────────────────

object DevProfile {
    const val name = "Alim"
    const val title = "Android & KMP Developer"
    const val tagline = "Building performant apps & open-source libraries for the Kotlin ecosystem"
    const val bio =
        "I'm an Android developer with 5+ years of experience shipping production apps and " +
        "Kotlin Multiplatform libraries. I specialise in Compose Multiplatform — delivering " +
        "shared UI and business logic across Android and iOS from a single codebase. " +
        "All 6 of my libraries are live on Maven Central and actively maintained."
    const val githubUrl = "https://github.com/Alims-Repo"
    const val email = "hello@alim.dev"
    const val yearsExperience = "5+"
    const val location = "Remote · Worldwide"
    const val availability = "Open to new projects"
}

// ─── Profile Stats ────────────────────────────────────────────────────────────

data class ProfileStat(val value: String, val label: String, val sublabel: String)

val profileStats = listOf(
    ProfileStat("6", "Libraries", "Maven Central"),
    ProfileStat("3+", "Apps", "Google Play"),
    ProfileStat("17K+", "Downloads", "Combined"),
    ProfileStat("64+", "Stars", "GitHub")
)

// ─── What I Do ────────────────────────────────────────────────────────────────

data class ServicePillar(
    val icon: String,
    val title: String,
    val subtitle: String,
    val accentColor: Color
)

val servicePillars = listOf(
    ServicePillar("📱", "Android Apps", "Jetpack Compose + Material3", Color(0xFF58A6FF)),
    ServicePillar("🔄", "Cross-Platform", "One codebase · Android + iOS", Color(0xFFA371F7)),
    ServicePillar("📦", "OSS Libraries", "Maven Central · KSP · KMP", Color(0xFF3FB950))
)

// ─── Skills ───────────────────────────────────────────────────────────────────

data class SkillCategory(val name: String, val accent: Color, val skills: List<String>)

val skillCategories = listOf(
    SkillCategory(
        "Mobile Development", Color(0xFF58A6FF),
        listOf("Kotlin", "Jetpack Compose", "Android SDK", "Material3", "XML Layouts", "ViewBinding")
    ),
    SkillCategory(
        "Cross-Platform (KMP / CMP)", Color(0xFFA371F7),
        listOf("Compose Multiplatform", "Kotlin Multiplatform", "iOS via KMP", "Desktop (JVM)", "Web (Wasm)")
    ),
    SkillCategory(
        "Architecture & Patterns", Color(0xFF3FB950),
        listOf("MVVM", "Clean Architecture", "Repository Pattern", "Hilt / Koin", "Coroutines", "Flow / StateFlow")
    ),
    SkillCategory(
        "Networking & Storage", Color(0xFFFFA657),
        listOf("Ktor", "Retrofit", "Room Database", "DataStore", "SQLDelight", "REST / JSON")
    ),
    SkillCategory(
        "Tooling & DevOps", Color(0xFFFF7B72),
        listOf("KSP / KSP2", "Gradle KTS", "Maven Central", "GitHub Actions", "CI/CD", "Proguard / R8")
    )
)

// ─── Experience ───────────────────────────────────────────────────────────────

data class ExperienceEntry(
    val title: String,
    val company: String,
    val period: String,
    val accentColor: Color,
    val highlights: List<String>
)

val experienceEntries = listOf(
    ExperienceEntry(
        title = "Android & KMP Developer",
        company = "Freelance · Self-employed",
        period = "2020 → Present",
        accentColor = Color(0xFF58A6FF),
        highlights = listOf(
            "Published 6 open-source libraries to Maven Central with 64+ GitHub stars",
            "Shipped 3 Android apps with 17K+ combined downloads on Google Play",
            "Built NavEase — an annotation-driven navigation library adopted by the community",
            "Specialised in Compose Multiplatform sharing UI across Android & iOS",
            "All libraries actively maintained with semantic versioning & changelogs"
        )
    ),
    ExperienceEntry(
        title = "Android Developer",
        company = "Mobile Projects",
        period = "2019 → 2020",
        accentColor = Color(0xFF3FB950),
        highlights = listOf(
            "Developed native Android apps with Kotlin and Java",
            "Progressively migrated XML layouts to Jetpack Compose",
            "Integrated REST APIs, push notifications, and local Room databases",
            "Applied MVVM architecture with LiveData, ViewModel, and Jetpack Navigation"
        )
    )
)

// ─── Services ─────────────────────────────────────────────────────────────────

data class ServiceTier(
    val title: String,
    val subtitle: String,
    val price: String,
    val unit: String,
    val accentColor: Color,
    val includes: List<String>,
    val isPopular: Boolean = false
)

val serviceTiers = listOf(
    ServiceTier(
        title = "Android App",
        subtitle = "Native Android with Jetpack Compose",
        price = "from \$500",
        unit = "per project",
        accentColor = Color(0xFF58A6FF),
        includes = listOf(
            "Jetpack Compose + Material3 UI",
            "MVVM clean architecture",
            "REST API & local storage",
            "Full source code delivery",
            "Play Store submission guidance",
            "30-day post-launch support"
        )
    ),
    ServiceTier(
        title = "Multiplatform App",
        subtitle = "Android + iOS via Compose Multiplatform",
        price = "from \$3,000",
        unit = "per project",
        accentColor = Color(0xFFA371F7),
        includes = listOf(
            "Single shared codebase (Android + iOS)",
            "Compose Multiplatform UI layer",
            "Shared networking, storage & logic",
            "Native-feel performance on both platforms",
            "Full docs + architecture overview",
            "60-day post-launch support"
        ),
        isPopular = true
    ),
    ServiceTier(
        title = "KMP Library",
        subtitle = "Published to Maven Central",
        price = "from \$1,500",
        unit = "per project",
        accentColor = Color(0xFF3FB950),
        includes = listOf(
            "Android, iOS, Desktop & Web targets",
            "KSP annotation processor (if required)",
            "Maven Central publishing & signing",
            "Full KDoc API documentation",
            "Unit + integration test coverage",
            "Optional ongoing maintenance plan"
        )
    )
)

data class HourlyRate(
    val title: String,
    val description: String,
    val rate: String,
    val accentColor: Color,
    val isHighlighted: Boolean = false
)

val hourlyRates = listOf(
    HourlyRate("Android Development", "Feature implementation, UI screens, SDK integration", "\$40 / hr", Color(0xFF58A6FF)),
    HourlyRate("KMP / CMP Development", "Cross-platform Kotlin development, iOS integration", "\$50 / hr", Color(0xFFA371F7), true),
    HourlyRate("Code Review", "Thorough review with detailed written feedback", "\$35 / hr", Color(0xFF3FB950)),
    HourlyRate("Technical Consultation", "Architecture, tech-stack selection, project planning", "\$40 / hr", Color(0xFFFFA657))
)

// ─── Published Apps ───────────────────────────────────────────────────────────

data class PublishedApp(
    val name: String,
    val tagline: String,
    val description: String,
    val category: String,
    val iconEmoji: String,
    val accentColor: Color,
    val playStoreUrl: String,
    val downloads: String,
    val rating: String,
    val ratingCount: String,
    val version: String,
    val lastUpdated: String,
    val size: String,
    val minAndroid: String,
    val features: List<String>,
    val isTopChart: Boolean = false
)

val publishedApps = listOf(
    PublishedApp(
        name = "Azan Times",
        tagline = "Prayer times, azan alerts & Qibla compass",
        description = "A full-featured Islamic companion for Muslims worldwide. Precise prayer times for " +
            "200+ countries, customisable azan sounds, live Qibla direction, and a Hijri calendar — all " +
            "in one elegant, completely ad-free app.",
        category = "Islamic",
        iconEmoji = "🕌",
        accentColor = Color(0xFF3FB950),
        playStoreUrl = "https://play.google.com/store/apps",
        downloads = "10K+",
        rating = "4.7",
        ratingCount = "284 ratings",
        version = "2.3.1",
        lastUpdated = "March 2026",
        size = "12.4 MB",
        minAndroid = "Android 7.0+",
        features = listOf(
            "Prayer times for 200+ countries",
            "15+ azan sounds, offline playback",
            "Live Qibla compass + degree reading",
            "Hijri calendar with Islamic events",
            "Home screen widget support",
            "Zero ads · completely free"
        ),
        isTopChart = true
    ),
    PublishedApp(
        name = "Quran Reader",
        tagline = "Complete Quran with audio recitations",
        description = "A beautifully crafted Quran app designed for distraction-free reading. Crystal-clear " +
            "Arabic typography, 6 translations, and 15 recitation styles from world-renowned reciters — " +
            "all with full offline support.",
        category = "Islamic",
        iconEmoji = "📖",
        accentColor = Color(0xFF58A6FF),
        playStoreUrl = "https://play.google.com/store/apps",
        downloads = "5K+",
        rating = "4.8",
        ratingCount = "147 ratings",
        version = "1.5.0",
        lastUpdated = "April 2026",
        size = "28.7 MB",
        minAndroid = "Android 7.0+",
        features = listOf(
            "Complete Quran with 6 translations",
            "15 reciters with offline audio",
            "Offline reading & audio support",
            "Tafsir (commentary) included",
            "Night mode for comfortable reading",
            "Bookmarks & verse highlighting"
        )
    ),
    PublishedApp(
        name = "PDF Maker",
        tagline = "Create professional PDFs on your phone",
        description = "Turn your content into polished PDF documents in seconds — no PC required. Write " +
            "text, build tables, insert images, and export anywhere. Built for invoices, business " +
            "reports, and study notes.",
        category = "Productivity",
        iconEmoji = "📄",
        accentColor = Color(0xFFFFA657),
        playStoreUrl = "https://play.google.com/store/apps",
        downloads = "2K+",
        rating = "4.5",
        ratingCount = "63 ratings",
        version = "1.2.0",
        lastUpdated = "January 2026",
        size = "6.8 MB",
        minAndroid = "Android 8.0+",
        features = listOf(
            "Text, tables & image support",
            "10+ professional document templates",
            "Share to any app in one tap",
            "Password-protect your documents",
            "A4, Letter & Legal page sizes",
            "Batch multi-document export"
        )
    )
)

