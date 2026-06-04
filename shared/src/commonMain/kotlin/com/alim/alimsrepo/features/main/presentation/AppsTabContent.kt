package com.alim.alimsrepo.features.main.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.alim.alimsrepo.core.data.PublishedApp
import com.alim.alimsrepo.core.data.publishedApps
import com.alim.alimsrepo.core.ui.theme.AccentCyan
import com.alim.alimsrepo.core.ui.theme.AccentGreen
import com.alim.alimsrepo.core.ui.theme.AccentGold
import com.alim.alimsrepo.core.ui.theme.CardShapeLarge
import com.alim.alimsrepo.core.ui.theme.ChipShape
import com.alim.alimsrepo.core.ui.theme.DarkBackground
import com.alim.alimsrepo.core.ui.theme.DarkSurface
import com.alim.alimsrepo.core.ui.theme.DarkSurfaceVariant
import com.alim.alimsrepo.core.ui.theme.ElevatedSurface
import com.alim.alimsrepo.core.ui.theme.PillShape
import com.alim.alimsrepo.core.ui.theme.TextPrimary
import com.alim.alimsrepo.core.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────────────────────────────────────
// Entry point
// ─────────────────────────────────────────────────────────────────────────────

@Composable
internal fun AppsTabContent(padding: PaddingValues) {
    val uriHandler = LocalUriHandler.current

    // Staggered entrance animation
    val visibleIndices = remember { mutableStateListOf<Int>() }
    LaunchedEffect(Unit) {
        for (i in 0 until 10) {
            delay(80L)
            visibleIndices.add(i)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(0),
                enter = fadeIn(tween(500)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(600)
                )
            ) {
                AppsHeaderCard()
            }
        }
        itemsIndexed(publishedApps) { index, app ->
            AnimatedVisibility(
                visible = visibleIndices.contains(index + 1),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                // Ambient glow behind card
                Box(Modifier.fillMaxWidth()) {
                    Box(
                        Modifier
                            .size(400.dp)
                            .offset(x = (-50).dp, y = (-50).dp)
                            .background(
                                Brush.radialGradient(
                                    listOf(
                                        app.accentColor.copy(0.04f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    AppCard(
                        app = app,
                        onPlayStoreClick = { uriHandler.openUri(app.playStoreUrl) }
                    )
                }
            }
        }
        item { Spacer(Modifier.height(8.dp)) }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Apps Header — with animated counters
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun AppsHeaderCard() {
    val totalDownloads = "17K+"
    val avgRating = "4.7"

    // Animated counters
    val appsCount = remember { Animatable(0f) }
    val downloadsCount = remember { Animatable(0f) }
    val ratingCount = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        delay(200)
        launch {
            appsCount.animateTo(
                publishedApps.size.toFloat(),
                tween(800, easing = FastOutSlowInEasing)
            )
        }
        launch {
            downloadsCount.animateTo(17f, tween(1000, easing = FastOutSlowInEasing))
        }
        launch {
            ratingCount.animateTo(4.7f, tween(1200, easing = FastOutSlowInEasing))
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = CardShapeLarge,
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(AccentCyan.copy(alpha = 0.11f), DarkSurface)
                    )
                )
        ) {
            // Ambient glow
            Box(
                Modifier
                    .size(300.dp)
                    .offset(x = 100.dp, y = (-80).dp)
                    .background(
                        Brush.radialGradient(
                            listOf(AccentCyan.copy(0.05f), Color.Transparent)
                        )
                    )
            )
            Column(Modifier.fillMaxWidth().padding(24.dp)) {
                // Play Store badge row with styled icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF3DDC84).copy(0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "▶",
                            color = Color(0xFF3DDC84),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        "GOOGLE PLAY STORE",
                        color = Color(0xFF3DDC84),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Published Apps",
                    color = TextPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-0.3).sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "All apps are live and actively maintained",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(22.dp))

                // Animated aggregate stats row
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AppAggregateStat(
                        "${appsCount.value.toInt()}",
                        "Apps Published",
                        AccentCyan,
                        Modifier.weight(1f)
                    )
                    AppAggregateStat(
                        "${downloadsCount.value.toInt()}K+",
                        "Total Downloads",
                        AccentGreen,
                        Modifier.weight(1f)
                    )
                    val ratingStr = ratingCount.value.let { v ->
                        val intPart = v.toInt()
                        val decPart = ((v - intPart) * 10).toInt()
                        "$intPart.$decPart"
                    }
                    AppAggregateStat(
                        "$ratingStr ★",
                        "Avg Rating",
                        AccentGold,
                        Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AppAggregateStat(
    value: String,
    label: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.verticalGradient(
                    listOf(ElevatedSurface, DarkSurfaceVariant)
                )
            )
            .border(1.dp, DarkSurfaceVariant, RoundedCornerShape(14.dp))
            .padding(vertical = 14.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            color = accent,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.height(2.dp))
        Text(
            label,
            color = TextSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 14.sp
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// App Card — Premium redesign
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun AppCard(app: PublishedApp, onPlayStoreClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    app.accentColor.copy(alpha = 0.2f),
                    RoundedCornerShape(20.dp)
                )
        ) {
            // Top gradient strip
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                app.accentColor,
                                app.accentColor.copy(0.35f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Column(Modifier.fillMaxWidth().padding(20.dp)) {

                // ── App Identity ──────────────────────────────────────────────
                Row(verticalAlignment = Alignment.Top) {
                    // Enlarged app icon with gradient
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        app.accentColor.copy(alpha = 0.2f),
                                        app.accentColor.copy(alpha = 0.08f)
                                    )
                                )
                            )
                            .border(
                                1.dp,
                                app.accentColor.copy(0.25f),
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(app.iconEmoji, fontSize = 38.sp)
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        // Name + badges
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                app.name,
                                color = TextPrimary,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (app.isTopChart) {
                                Box(
                                    Modifier
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(AccentGold.copy(0.18f))
                                        .border(
                                            1.dp,
                                            AccentGold.copy(0.35f),
                                            RoundedCornerShape(5.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        "TOP",
                                        color = AccentGold,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                        }
                        Spacer(Modifier.height(3.dp))
                        Text(
                            app.tagline,
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        // Rating + Downloads + Category
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            VisualStarRating(app.rating, app.ratingCount)
                            DownloadsChip(app.downloads)
                            CategoryChip(app.category, app.accentColor)
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                // ── Description ───────────────────────────────────────────────
                Text(
                    app.description,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 21.sp
                )

                Spacer(Modifier.height(18.dp))

                // ── Key Features ──────────────────────────────────────────────
                Text(
                    "KEY FEATURES",
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.height(10.dp))

                // 2-column feature list with styled dots
                val featurePairs = app.features.chunked(2)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    featurePairs.forEach { pair ->
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            pair.forEach { feature ->
                                Row(
                                    Modifier.weight(1f),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        Modifier
                                            .padding(top = 5.dp)
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(app.accentColor)
                                    )
                                    Text(
                                        feature,
                                        color = TextSecondary,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp
                                    )
                                }
                            }
                            if (pair.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(Modifier.height(14.dp))

                // ── Metadata + CTA ────────────────────────────────────────────
                Row(
                    Modifier.fillMaxWidth(),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    // App metadata with styled indicators
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            MetadataChip("v${app.version}", app.accentColor)
                            MetadataLabel(app.size)
                            MetadataLabel(app.minAndroid)
                        }
                        Spacer(Modifier.height(3.dp))
                        Text(
                            "Updated ${app.lastUpdated}",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }
                    // Play Store button — Google Play green
                    Button(
                        onClick = onPlayStoreClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3DDC84),
                            contentColor = Color(0xFF0D1117)
                        ),
                        contentPadding = PaddingValues(
                            horizontal = 18.dp,
                            vertical = 10.dp
                        )
                    ) {
                        Text(
                            "Play Store →",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Visual Star Rating
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun VisualStarRating(rating: String, count: String) {
    val ratingValue = rating.toFloatOrNull() ?: 0f
    val fullStars = ratingValue.toInt()
    val hasHalf = (ratingValue - fullStars) >= 0.3f

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier
            .clip(ChipShape)
            .background(AccentGold.copy(0.12f))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        // Visual stars
        for (i in 1..5) {
            Text(
                text = if (i <= fullStars) "★"
                       else if (i == fullStars + 1 && hasHalf) "★"
                       else "☆",
                color = if (i <= fullStars || (i == fullStars + 1 && hasHalf))
                    AccentGold
                else
                    AccentGold.copy(0.3f),
                fontSize = 10.sp
            )
        }
        Spacer(Modifier.width(2.dp))
        Text(
            rating,
            color = AccentGold,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "(${count})",
            color = TextSecondary,
            fontSize = 9.sp
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Enhanced Downloads Chip
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun DownloadsChip(downloads: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .clip(ChipShape)
            .background(DarkSurfaceVariant)
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text("↑", color = AccentGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Text(
            downloads,
            color = TextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun CategoryChip(category: String, accent: Color) {
    Box(
        Modifier
            .clip(ChipShape)
            .background(accent.copy(0.13f))
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text(
            category,
            color = accent,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun MetadataChip(text: String, accent: Color) {
    Box(
        Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(accent.copy(0.1f))
            .padding(horizontal = 6.dp, vertical = 1.dp)
    ) {
        Text(
            text = text,
            color = accent,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun MetadataLabel(text: String) {
    Text(
        text = text,
        color = TextSecondary,
        fontSize = 11.sp
    )
}
