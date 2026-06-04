@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.alim.alimsrepo.features.main.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.alim.alimsrepo.app.AppScreens
import com.alim.alimsrepo.core.data.allLibraries
import com.alim.alimsrepo.core.domain.model.Library
import com.alim.alimsrepo.core.ui.theme.AccentCyan
import com.alim.alimsrepo.core.ui.theme.AccentGreen
import com.alim.alimsrepo.core.ui.theme.AccentPurple
import com.alim.alimsrepo.core.ui.theme.DarkBackground
import com.alim.alimsrepo.core.ui.theme.DarkSurface
import com.alim.alimsrepo.core.ui.theme.DarkSurfaceVariant
import com.alim.alimsrepo.core.ui.theme.TextPrimary
import com.alim.alimsrepo.core.ui.theme.TextSecondary
import io.github.alimsrepo.navease.runtime.annotations.AutoRegister
import io.github.alimsrepo.navease.runtime.navigation.NavController
import io.github.alimsrepo.navease.runtime.presentation.ActivityScreen
import io.github.alimsrepo.navease.runtime.presentation.LocalNavEaseSharedTransitionScope
import kotlinx.coroutines.delay

// ─────────────────────────────────────────────────────────────────────────────

@AutoRegister
class MainScreen : ActivityScreen<AppScreens.Main>() {

    private enum class Tab(val label: String, val icon: String) {
        About("About", "👤"),
        Apps("Apps", "📱"),
        Libraries("Libs", "📦")
    }

    // ── Entry point ───────────────────────────────────────────────────────────

    @Composable
    override fun Content(navKey: AppScreens.Main, navController: NavController) {
        var selectedTab by remember { mutableStateOf(Tab.About) }
        val visibleItems = remember { mutableStateListOf<Library>() }

        LaunchedEffect(Unit) {
            allLibraries.forEachIndexed { index, library ->
                delay(index * 80L)
                visibleItems.add(library)
            }
        }

        Scaffold(
            topBar = {
                when (selectedTab) {
                    Tab.About     -> AboutTopBar()
                    Tab.Apps      -> AppsTopBar()
                    Tab.Libraries -> LibrariesTopBar()
                }
            },
            bottomBar = { BottomNav(selectedTab) { selectedTab = it } },
            containerColor = DarkBackground
        ) { padding ->
            Crossfade(targetState = selectedTab, animationSpec = tween(200), modifier = Modifier.fillMaxSize()) { tab ->
                when (tab) {
                    Tab.About     -> AboutTabContent(padding)
                    Tab.Apps      -> AppsTabContent(padding)
                    Tab.Libraries -> LibrariesTabContent(padding, visibleItems, navController)
                }
            }
        }
    }

    // ── Bottom Navigation ─────────────────────────────────────────────────────

    @Composable
    private fun BottomNav(selectedTab: Tab, onTabSelected: (Tab) -> Unit) {
        Column {
            Box(
                Modifier.fillMaxWidth().height(1.dp).background(
                    Brush.horizontalGradient(
                        listOf(AccentGreen.copy(0.25f), AccentCyan.copy(0.25f), AccentPurple.copy(0.25f), Color.Transparent)
                    )
                )
            )
            NavigationBar(containerColor = DarkSurface, tonalElevation = 0.dp) {
                Tab.entries.forEach { tab ->
                    val isSelected = selectedTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onTabSelected(tab) },
                        icon = { Text(tab.icon, fontSize = 19.sp) },
                        label = { Text(tab.label, fontSize = 11.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AccentCyan, selectedTextColor = AccentCyan,
                            indicatorColor = AccentCyan.copy(0.13f),
                            unselectedIconColor = TextSecondary, unselectedTextColor = TextSecondary
                        )
                    )
                }
            }
        }
    }

    // ── Top Bars ──────────────────────────────────────────────────────────────

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AboutTopBar() = StyledTopBar(
        avatarGradient = listOf(AccentGreen, AccentCyan),
        title = "About",
        subtitle = "Android & KMP Developer",
        underlineColors = listOf(AccentGreen.copy(0.7f), AccentCyan.copy(0.4f), Color.Transparent)
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun AppsTopBar() = StyledTopBar(
        avatarGradient = listOf(AccentCyan, Color(0xFF3DDC84)),
        title = "Apps",
        subtitle = "Published on Google Play",
        underlineColors = listOf(AccentCyan.copy(0.7f), AccentGreen.copy(0.4f), Color.Transparent)
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LibrariesTopBar() = StyledTopBar(
        avatarGradient = listOf(AccentCyan, AccentPurple),
        title = "Libraries",
        subtitle = "Published on Maven Central",
        underlineColors = listOf(AccentCyan.copy(0.7f), AccentPurple.copy(0.5f), Color.Transparent)
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun StyledTopBar(
        avatarGradient: List<Color>,
        title: String,
        subtitle: String,
        underlineColors: List<Color>
    ) {
        Column {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier.size(36.dp).background(Brush.linearGradient(avatarGradient), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) { Text("A", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp) }
                        Spacer(Modifier.width(11.dp))
                        Column {
                            Text(title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp, lineHeight = 19.sp)
                            Text(subtitle, color = TextSecondary, fontSize = 11.sp, lineHeight = 14.sp)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground.copy(0.97f))
            )
            Box(Modifier.fillMaxWidth().height(1.5.dp).background(Brush.horizontalGradient(underlineColors)))
        }
    }

    // ── Libraries Tab ─────────────────────────────────────────────────────────

    @Composable
    private fun LibrariesTabContent(
        padding: PaddingValues,
        visibleItems: List<Library>,
        navController: NavController
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 320.dp),
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) { LibrariesHeader() }
            items(allLibraries, key = { it.id }) { library ->
                AnimatedVisibility(
                    visible = visibleItems.contains(library),
                    enter = fadeIn(animationSpec = tween(400)) +
                            slideInVertically(initialOffsetY = { it / 2 }, animationSpec = tween(500))
                ) {
                    LibraryCard(library) {
                        navController.navigate(AppScreens.LibraryDetail(libraryId = library.id))
                    }
                }
            }
        }
    }

    @Composable
    private fun LibrariesHeader() {
        val kmpCount = allLibraries.count { it.platforms.size > 1 }
        val totalStars = allLibraries.mapNotNull { it.stars }.sum()
        Column(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Open-Source Libraries", style = MaterialTheme.typography.headlineMedium, color = TextPrimary, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.height(4.dp))
            Text("${allLibraries.size} libraries on Maven Central · Kotlin & Compose", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                LibStatChip("${allLibraries.size} Libraries", AccentCyan)
                LibStatChip("$kmpCount Multiplatform", AccentPurple)
                LibStatChip("$totalStars+ Stars", AccentGreen)
            }
        }
    }

    // ── Library Card ──────────────────────────────────────────────────────────

    @Composable
    private fun LibraryCard(library: Library, onClick: () -> Unit) {
        val sharedScope = LocalNavEaseSharedTransitionScope.current
        val animatedScope = LocalNavAnimatedContentScope.current
        val sharedMod = if (sharedScope != null && animatedScope != null) {
            with(sharedScope) {
                Modifier.sharedBounds(
                    rememberSharedContentState("lib_card_${library.id}"),
                    animatedScope,
                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    placeholderSize = SharedTransitionScope.PlaceholderSize.AnimatedSize
                )
            }
        } else Modifier

        Card(
            modifier = sharedMod.fillMaxWidth().clickable(onClick = onClick)
                .border(1.dp, Brush.linearGradient(listOf(library.accentColor.copy(0.38f), DarkSurfaceVariant.copy(0.5f))), RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                Box(Modifier.fillMaxWidth().height(3.dp).background(
                    Brush.horizontalGradient(listOf(library.accentColor, library.accentColor.copy(0.35f), Color.Transparent))
                ))
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.Top) {
                        Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(10.dp).clip(CircleShape).background(library.accentColor))
                            Spacer(Modifier.width(10.dp))
                            Text(library.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Badge(containerColor = library.accentColor.copy(0.15f), contentColor = library.accentColor) {
                                Text(library.version, Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                            if (library.stars != null && library.stars > 0) {
                                Spacer(Modifier.height(4.dp))
                                Text("★ ${library.stars}", color = Color(0xFFFFA657), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    // Category chip
                    Box(
                        Modifier.clip(RoundedCornerShape(5.dp)).background(library.accentColor.copy(0.12f))
                            .border(1.dp, library.accentColor.copy(0.2f), RoundedCornerShape(5.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(library.category.uppercase(), color = library.accentColor, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.8.sp)
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(library.description, color = TextSecondary, fontSize = 14.sp, lineHeight = 22.sp, maxLines = 3)
                    Spacer(Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        library.platforms.forEach { PlatformChip(it, library.accentColor) }
                    }
                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = DarkSurfaceVariant)
                    Spacer(Modifier.height(12.dp))
                    Box(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(DarkBackground)
                            .border(1.dp, DarkSurfaceVariant, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(library.artifact, color = AccentGreen.copy(0.85f), fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }

    @Composable
    private fun PlatformChip(name: String, accent: Color) {
        Box(
            Modifier.clip(RoundedCornerShape(6.dp)).background(accent.copy(0.11f))
                .border(1.dp, accent.copy(0.2f), RoundedCornerShape(6.dp))
                .padding(horizontal = 9.dp, vertical = 4.dp)
        ) { Text(name, color = accent.copy(0.9f), fontSize = 11.sp, fontWeight = FontWeight.SemiBold) }
    }

    @Composable
    private fun LibStatChip(label: String, accent: Color) {
        Box(
            Modifier.clip(RoundedCornerShape(20.dp)).background(accent.copy(0.11f))
                .border(1.dp, accent.copy(0.25f), RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) { Text(label, color = accent, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
    }
}

