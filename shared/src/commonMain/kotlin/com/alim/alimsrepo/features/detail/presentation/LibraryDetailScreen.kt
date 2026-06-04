@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.alim.alimsrepo.features.detail.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alim.alimsrepo.app.AppScreens
import com.alim.alimsrepo.core.data.libraryById
import com.alim.alimsrepo.core.domain.model.Library
import com.alim.alimsrepo.core.ui.theme.AccentCyan
import com.alim.alimsrepo.core.ui.theme.AccentGreen
import com.alim.alimsrepo.core.ui.theme.AccentOrange
import com.alim.alimsrepo.core.ui.theme.AccentPurple
import com.alim.alimsrepo.core.ui.theme.AccentRed
import com.alim.alimsrepo.core.ui.theme.ChipShape
import com.alim.alimsrepo.core.ui.theme.DarkBackground
import com.alim.alimsrepo.core.ui.theme.DarkSurface
import com.alim.alimsrepo.core.ui.theme.DarkSurfaceVariant
import com.alim.alimsrepo.core.ui.theme.TextPrimary
import com.alim.alimsrepo.core.ui.theme.TextSecondary
import io.github.alimsrepo.navease.internal.navigation.ui.LocalNavAnimatedContentScope
import io.github.alimsrepo.navease.runtime.annotations.AutoRegister
import io.github.alimsrepo.navease.runtime.navigation.NavEaseController
import io.github.alimsrepo.navease.runtime.presentation.LocalNavEaseSharedTransitionScope
import io.github.alimsrepo.navease.runtime.screen.ActivityScreen
import kotlinx.coroutines.delay

@AutoRegister
class LibraryDetailScreen : ActivityScreen<AppScreens.LibraryDetail>() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content(
        navKey: AppScreens.LibraryDetail,
        navEaseController: NavEaseController
    ) {
        val library = libraryById(navKey.libraryId) ?: run {
            Text("Library not found", color = TextPrimary)
            return
        }

        val sharedScope = LocalNavEaseSharedTransitionScope.current
        val animatedScope = LocalNavAnimatedContentScope.current

        // Shared bounds modifier — morphs from the list card
        val heroSharedModifier = if (sharedScope != null) {
            with(sharedScope) {
                Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "lib_card_${library.id}"),
                    animatedVisibilityScope = animatedScope,
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    placeholderSize = SharedTransitionScope.PlaceholderSize.AnimatedSize,
                )
            }
        } else Modifier

        // Content entrance animation (after shared element settles)
        var contentVisible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            delay(250)
            contentVisible = true
        }

        Scaffold(
            topBar = {
                DetailTopBar(
                    library = library,
                    onBack = { navEaseController.back() }
                )
            },
            containerColor = DarkBackground
        ) { padding ->
            Box(Modifier.fillMaxSize()) {
                // Ambient gradient background behind hero
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    library.accentColor.copy(0.08f),
                                    DarkBackground
                                )
                            )
                        )
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // ── Hero card (shared bounds with MainScreen card) ────────
                    item {
                        HeroCard(library = library, modifier = heroSharedModifier)
                    }

                    // ── About ─────────────────────────────────────────────────
                    item {
                        AnimatedVisibility(
                            visible = contentVisible,
                            enter = fadeIn(tween(400)) + slideInVertically(
                                initialOffsetY = { it / 4 },
                                animationSpec = tween(500)
                            )
                        ) {
                            SectionCard(
                                title = "ABOUT",
                                accent = library.accentColor
                            ) {
                                Text(
                                    text = library.description,
                                    color = TextSecondary,
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp
                                )
                            }
                        }
                    }

                    // ── Features ──────────────────────────────────────────────
                    if (library.features.isNotEmpty()) {
                        item {
                            AnimatedVisibility(
                                visible = contentVisible,
                                enter = fadeIn(tween(400, delayMillis = 80)) + slideInVertically(
                                    initialOffsetY = { it / 4 },
                                    animationSpec = tween(500, delayMillis = 80)
                                )
                            ) {
                                SectionCard(
                                    title = "FEATURES",
                                    accent = library.accentColor
                                ) {
                                    library.features.forEachIndexed { index, feature ->
                                        if (index > 0) Spacer(Modifier.height(10.dp))
                                        Row(
                                            verticalAlignment = Alignment.Top,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .clip(CircleShape)
                                                    .background(
                                                        Brush.linearGradient(
                                                            listOf(
                                                                library.accentColor.copy(0.2f),
                                                                library.accentColor.copy(0.1f)
                                                            )
                                                        )
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "✓",
                                                    color = library.accentColor,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Text(
                                                text = feature,
                                                color = TextSecondary,
                                                fontSize = 14.sp,
                                                lineHeight = 20.sp,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // ── Installation ──────────────────────────────────────────
                    item {
                        AnimatedVisibility(
                            visible = contentVisible,
                            enter = fadeIn(tween(400, delayMillis = 160)) + slideInVertically(
                                initialOffsetY = { it / 4 },
                                animationSpec = tween(500, delayMillis = 160)
                            )
                        ) {
                            SectionCard(
                                title = "INSTALLATION — MAVEN CENTRAL",
                                accent = library.accentColor
                            ) {
                                Text(
                                    text = "Add to your Gradle dependencies:",
                                    color = TextSecondary,
                                    fontSize = 13.sp
                                )
                                Spacer(Modifier.height(10.dp))
                                // Enhanced Gradle KTS snippet
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(ChipShape)
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    DarkBackground,
                                                    Color(0xFF0A0E14)
                                                )
                                            )
                                        )
                                        .border(
                                            1.dp,
                                            library.accentColor.copy(alpha = 0.2f),
                                            ChipShape
                                        )
                                        .padding(14.dp)
                                ) {
                                    Column {
                                        // Gradle KTS label
                                        Row(
                                            Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            // Line number
                                            Text(
                                                text = "1",
                                                color = TextSecondary.copy(0.3f),
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                            Box(
                                                Modifier
                                                    .clip(RoundedCornerShape(3.dp))
                                                    .background(
                                                        library.accentColor.copy(0.1f)
                                                    )
                                                    .padding(
                                                        horizontal = 6.dp,
                                                        vertical = 1.dp
                                                    )
                                            ) {
                                                Text(
                                                    "Gradle KTS",
                                                    color = library.accentColor.copy(0.7f),
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = FontFamily.Monospace
                                                )
                                            }
                                        }
                                        Spacer(Modifier.height(6.dp))
                                        // Comment
                                        Row {
                                            Text(
                                                text = "2  ",
                                                color = TextSecondary.copy(0.3f),
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                            Text(
                                                text = "// build.gradle.kts",
                                                color = TextSecondary.copy(alpha = 0.4f),
                                                fontSize = 11.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                        Spacer(Modifier.height(4.dp))
                                        // Implementation line with syntax coloring
                                        Row {
                                            Text(
                                                text = "3  ",
                                                color = TextSecondary.copy(0.3f),
                                                fontSize = 10.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                            Text(
                                                text = "implementation",
                                                color = AccentCyan,
                                                fontSize = 12.sp,
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = "(\"",
                                                color = TextSecondary.copy(0.6f),
                                                fontSize = 12.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                            Text(
                                                text = "${library.artifact}:${library.version}",
                                                color = AccentGreen,
                                                fontSize = 12.sp,
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Medium
                                            )
                                            Text(
                                                text = "\")",
                                                color = TextSecondary.copy(0.6f),
                                                fontSize = 12.sp,
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                    }
                                }
                                Spacer(Modifier.height(12.dp))
                                HorizontalDivider(color = DarkSurfaceVariant)
                                Spacer(Modifier.height(12.dp))
                                // Maven artifact info
                                MavenInfoRow("Group ID", library.artifact.substringBeforeLast(":"), TextPrimary)
                                Spacer(Modifier.height(6.dp))
                                MavenInfoRow("Artifact", library.artifact.substringAfterLast(":"), TextPrimary)
                                Spacer(Modifier.height(6.dp))
                                MavenInfoRow("Version", library.version, library.accentColor)
                            }
                        }
                    }

                    // ── Links ─────────────────────────────────────────────────
                    item {
                        AnimatedVisibility(
                            visible = contentVisible,
                            enter = fadeIn(tween(400, delayMillis = 240)) + slideInVertically(
                                initialOffsetY = { it / 4 },
                                animationSpec = tween(500, delayMillis = 240)
                            )
                        ) {
                            LinksCard(library = library)
                        }
                    }

                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
        }
    }
}

// ─── Maven Info Row ─────────────────────────────────────────────────────────

@Composable
private fun MavenInfoRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label.padEnd(10),
            color = TextSecondary,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = if (valueColor != TextPrimary) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

// ─── Platform Color Helper ──────────────────────────────────────────────────

private fun platformColor(platform: String): Color = when (platform.lowercase()) {
    "android" -> AccentGreen
    "ios" -> AccentCyan
    "desktop" -> AccentPurple
    "web" -> AccentOrange
    "jvm" -> AccentRed
    else -> AccentCyan
}

// ─── Hero Card ──────────────────────────────────────────────────────────────

@Composable
private fun HeroCard(library: Library, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top accent gradient strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                library.accentColor,
                                library.accentColor.copy(alpha = 0.4f),
                                Color.Transparent
                            )
                        )
                    )
            )
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
                // Name row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(library.accentColor)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = library.name,
                            color = TextPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        // Category chip
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(library.accentColor.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = library.category.uppercase(),
                                color = library.accentColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        }
                        if (library.stars != null && library.stars > 0) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "★ ${library.stars}",
                                color = Color(0xFFFFA657),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(modifier = Modifier.height(14.dp))

                // Version + platforms with platform-specific colors
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Version badge
                    Box(
                        modifier = Modifier
                            .clip(ChipShape)
                            .background(library.accentColor.copy(alpha = 0.15f))
                            .border(
                                1.dp,
                                library.accentColor.copy(alpha = 0.3f),
                                ChipShape
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = library.version,
                            color = library.accentColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Platform chips with platform-specific colors
                    library.platforms.forEach { platform ->
                        val pColor = platformColor(platform)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(pColor.copy(0.1f))
                                .border(1.dp, pColor.copy(0.2f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = platform,
                                color = pColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─── Section Card ────────────────────────────────────────────────────────────

@Composable
private fun SectionCard(
    title: String,
    accent: Color,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            // Top accent strip
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                accent.copy(0.5f),
                                accent.copy(0.15f),
                                Color.Transparent
                            )
                        )
                    )
            )
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(14.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(accent)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        color = accent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                content()
            }
        }
    }
}

// ─── Links Card ──────────────────────────────────────────────────────────────

@Composable
private fun LinksCard(library: Library) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            // Top accent strip
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                library.accentColor.copy(0.5f),
                                library.accentColor.copy(0.15f),
                                Color.Transparent
                            )
                        )
                    )
            )
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(3.dp)
                            .height(14.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(library.accentColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "LINKS",
                        color = library.accentColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // GitHub button
                    Button(
                        onClick = { uriHandler.openUri(library.githubUrl) },
                        modifier = Modifier.weight(1f).height(42.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = library.accentColor,
                            contentColor = Color(0xFF0D1117)
                        )
                    ) {
                        Text(
                            text = "</> GitHub",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Maven button
                    OutlinedButton(
                        onClick = { uriHandler.openUri(library.mavenUrl) },
                        modifier = Modifier.weight(1f).height(42.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            1.dp,
                            library.accentColor.copy(alpha = 0.5f)
                        )
                    ) {
                        Text(
                            text = "◻ Maven",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = library.accentColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = library.githubUrl,
                    color = AccentCyan.copy(alpha = 0.6f),
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = library.mavenUrl,
                    color = TextSecondary.copy(alpha = 0.5f),
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

// ─── Detail Top Bar ──────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(library: Library, onBack: () -> Unit) {
    Column {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = library.name,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = library.category,
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 14.sp
                    )
                }
            },
            navigationIcon = {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(DarkSurfaceVariant)
                        .border(1.dp, DarkSurfaceVariant.copy(0.8f), CircleShape)
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "←",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = DarkBackground.copy(alpha = 0.97f),
                titleContentColor = TextPrimary
            )
        )
        // Gradient accent underline
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            library.accentColor.copy(alpha = 0.7f),
                            library.accentColor.copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}
