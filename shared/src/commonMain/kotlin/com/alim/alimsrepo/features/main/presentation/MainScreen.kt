@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.alim.alimsrepo.features.main.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.alim.alimsrepo.app.AppScreens
import com.alim.alimsrepo.core.data.allLibraries
import com.alim.alimsrepo.core.domain.model.Library
import com.alim.alimsrepo.core.ui.theme.AccentCyan
import com.alim.alimsrepo.core.ui.theme.AccentGreen
import com.alim.alimsrepo.core.ui.theme.AccentPurple
import com.alim.alimsrepo.core.ui.theme.ChipShape
import com.alim.alimsrepo.core.ui.theme.DarkBackground
import com.alim.alimsrepo.core.ui.theme.DarkSurface
import com.alim.alimsrepo.core.ui.theme.DarkSurfaceVariant
import com.alim.alimsrepo.core.ui.theme.PillShape
import com.alim.alimsrepo.core.ui.theme.TextPrimary
import com.alim.alimsrepo.core.ui.theme.TextSecondary
import io.github.alimsrepo.navease.internal.navigation.ui.LocalNavAnimatedContentScope
import io.github.alimsrepo.navease.runtime.annotations.AutoRegister
import io.github.alimsrepo.navease.runtime.navigation.NavEaseController
import io.github.alimsrepo.navease.runtime.presentation.LocalNavEaseSharedTransitionScope
import io.github.alimsrepo.navease.runtime.screen.ActivityScreen
import kotlinx.coroutines.delay

// ─────────────────────────────────────────────────────────────────────────────

@AutoRegister
class MainScreen : ActivityScreen<AppScreens.Main>() {

    private enum class Tab(val label: String) {
        About("About"),
        Apps("Apps"),
        Libraries("Libs")
    }

    // ── Entry point ───────────────────────────────────────────────────────────

    @Composable
    override fun Content(navKey: AppScreens.Main, navEaseController: NavEaseController) {
        var selectedTab by remember { mutableStateOf(Tab.About) }
        val visibleItems = remember { mutableStateListOf<Library>() }

        LaunchedEffect(Unit) {
            allLibraries.forEachIndexed { index, library ->
                delay(index * 80L)
                visibleItems.add(library)
            }
        }

        Scaffold(
            bottomBar = { BottomNav(selectedTab) { selectedTab = it } },
            containerColor = DarkBackground
        ) { padding ->
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    (fadeIn(tween(300)) + slideInVertically(
                        initialOffsetY = { it / 10 },
                        animationSpec = tween(350)
                    )) togetherWith (fadeOut(tween(200)) + slideOutVertically(
                        targetOffsetY = { -it / 10 },
                        animationSpec = tween(200)
                    )) using SizeTransform(clip = false)
                },
                modifier = Modifier.fillMaxSize(),
                label = "tab_content"
            ) { tab ->
                when (tab) {
                    Tab.About     -> AboutTabContent(padding)
                    Tab.Apps      -> AppsTabContent(padding)
                    Tab.Libraries -> LibrariesTabContent(padding, visibleItems, navEaseController)
                }
            }
        }
    }

    // ── Bottom Navigation ─────────────────────────────────────────────────────

    @Composable
    private fun BottomNav(selectedTab: Tab, onTabSelected: (Tab) -> Unit) {
        val infiniteTransition = rememberInfiniteTransition(label = "nav_shimmer")
        val shimmerOffset by infiniteTransition.animateFloat(
            initialValue = -1f,
            targetValue = 2f,
            animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
            label = "shimmer_offset"
        )

        Column {
            // Animated shimmer gradient line
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                AccentGreen.copy(0.4f),
                                AccentCyan.copy(0.6f),
                                AccentPurple.copy(0.4f),
                                Color.Transparent
                            ),
                            startX = shimmerOffset * 800f,
                            endX = (shimmerOffset + 1f) * 800f
                        )
                    )
            )
            NavigationBar(containerColor = DarkSurface, tonalElevation = 0.dp) {
                Tab.entries.forEach { tab ->
                    val isSelected = selectedTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onTabSelected(tab) },
                        icon = { TabIcon(tab, isSelected) },
                        label = {
                            Text(
                                tab.label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AccentCyan,
                            selectedTextColor = AccentCyan,
                            indicatorColor = AccentCyan.copy(0.13f),
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun TabIcon(tab: Tab, isSelected: Boolean) {
        val accent = when (tab) {
            Tab.About -> AccentGreen
            Tab.Apps -> Color(0xFF3DDC84)
            Tab.Libraries -> AccentPurple
        }
        val bg = if (isSelected) accent.copy(0.2f) else DarkSurfaceVariant
        val fg = if (isSelected) accent else TextSecondary
        val symbol = when (tab) {
            Tab.About -> "A"
            Tab.Apps -> "▶"
            Tab.Libraries -> "◇"
        }
        Box(
            Modifier
                .size(22.dp)
                .clip(if (tab == Tab.About) CircleShape else RoundedCornerShape(5.dp))
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                symbol,
                color = fg,
                fontSize = if (tab == Tab.Apps) 9.sp else 11.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }


    // ── Libraries Tab ─────────────────────────────────────────────────────────

    @Composable
    private fun LibrariesTabContent(
        padding: PaddingValues,
        visibleItems: List<Library>,
        navController: NavEaseController
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
                            slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = tween(500)
                            )
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

        Box(Modifier.fillMaxWidth()) {
            // Ambient glow behind header
            Box(
                Modifier
                    .size(300.dp)
                    .offset(x = (-50).dp, y = (-80).dp)
                    .background(
                        Brush.radialGradient(
                            listOf(AccentCyan.copy(alpha = 0.04f), Color.Transparent)
                        )
                    )
            )
            Column(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Text(
                    "Open-Source Libraries",
                    color = TextPrimary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp,
                    lineHeight = 34.sp,
                    letterSpacing = (-0.3).sp
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "${allLibraries.size} libraries on Maven Central · Kotlin & Compose",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                Spacer(Modifier.height(18.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    LibStatChip("${allLibraries.size} Libraries", AccentCyan)
                    LibStatChip("$kmpCount Multiplatform", AccentPurple)
                    LibStatChip("$totalStars+ Stars", AccentGreen)
                }
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
            modifier = sharedMod
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .border(
                    1.dp,
                    Brush.linearGradient(
                        listOf(
                            library.accentColor.copy(0.38f),
                            DarkSurfaceVariant.copy(0.5f)
                        )
                    ),
                    RoundedCornerShape(20.dp)
                ),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                // Top accent gradient strip
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(3.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    library.accentColor,
                                    library.accentColor.copy(0.35f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.Top
                    ) {
                        Row(
                            Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(library.accentColor)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                library.name,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Badge(
                                containerColor = library.accentColor.copy(0.15f),
                                contentColor = library.accentColor
                            ) {
                                Text(
                                    library.version,
                                    Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            if (library.stars != null && library.stars > 0) {
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "★ ${library.stars}",
                                    color = Color(0xFFFFA657),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    // Category chip
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(library.accentColor.copy(0.12f))
                            .border(
                                1.dp,
                                library.accentColor.copy(0.2f),
                                RoundedCornerShape(5.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            library.category.uppercase(),
                            color = library.accentColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.8.sp
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        library.description,
                        color = TextSecondary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        maxLines = 3
                    )
                    Spacer(Modifier.height(14.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        library.platforms.forEach { PlatformChip(it, library.accentColor) }
                    }
                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = DarkSurfaceVariant)
                    Spacer(Modifier.height(12.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .clip(ChipShape)
                            .background(DarkBackground)
                            .border(1.dp, DarkSurfaceVariant, ChipShape)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            library.artifact,
                            color = AccentGreen.copy(0.85f),
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun PlatformChip(name: String, accent: Color) {
        Box(
            Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(accent.copy(0.11f))
                .border(1.dp, accent.copy(0.2f), RoundedCornerShape(6.dp))
                .padding(horizontal = 9.dp, vertical = 4.dp)
        ) {
            Text(
                name,
                color = accent.copy(0.9f),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    @Composable
    private fun LibStatChip(label: String, accent: Color) {
        Box(
            Modifier
                .clip(PillShape)
                .background(accent.copy(0.11f))
                .border(1.dp, accent.copy(0.25f), PillShape)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                label,
                color = accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
