package com.alim.alimsrepo.features.main.presentation

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.alim.alimsrepo.core.ui.theme.DarkBackground
import com.alim.alimsrepo.core.ui.theme.DarkSurface
import com.alim.alimsrepo.core.ui.theme.DarkSurfaceVariant
import com.alim.alimsrepo.core.ui.theme.TextPrimary
import com.alim.alimsrepo.core.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────────────────
// Entry point
// ─────────────────────────────────────────────────────────────────────────────

@Composable
internal fun AppsTabContent(padding: PaddingValues) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item { AppsHeaderCard() }
        items(publishedApps) { app ->
            AppCard(app = app, onPlayStoreClick = { uriHandler.openUri(app.playStoreUrl) })
        }
        item { Spacer(Modifier.height(8.dp)) }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Apps Header
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun AppsHeaderCard() {
    // Aggregate stats
    val totalDownloads = "17K+"
    val avgRating = "4.7"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(AccentCyan.copy(alpha = 0.11f), DarkSurface)))) {
            Column(Modifier.fillMaxWidth().padding(24.dp)) {
                // Play Store badge row
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("▶", color = Color(0xFF3DDC84), fontSize = 14.sp)
                    Text("GOOGLE PLAY STORE", color = Color(0xFF3DDC84), fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                }
                Spacer(Modifier.height(14.dp))
                Text("Published Apps", color = TextPrimary, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(4.dp))
                Text("All apps are live and actively maintained", color = TextSecondary, fontSize = 14.sp)
                Spacer(Modifier.height(20.dp))

                // Aggregate stats row
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AppAggregateStat("${publishedApps.size}", "Apps Published", AccentCyan, Modifier.weight(1f))
                    AppAggregateStat(totalDownloads, "Total Downloads", AccentGreen, Modifier.weight(1f))
                    AppAggregateStat("$avgRating ★", "Avg Rating", Color(0xFFFFA657), Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun AppAggregateStat(value: String, label: String, accent: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurfaceVariant)
            .padding(vertical = 12.dp, horizontal = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(value, color = accent, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(2.dp))
        Text(label, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Medium, lineHeight = 14.sp)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// App Card
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
            Modifier.fillMaxWidth()
                .border(1.dp, app.accentColor.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
        ) {
            // Top gradient strip
            Box(Modifier.fillMaxWidth().height(3.dp).background(
                Brush.horizontalGradient(listOf(app.accentColor, app.accentColor.copy(0.35f), Color.Transparent))
            ))

            Column(Modifier.fillMaxWidth().padding(20.dp)) {

                // ── App Identity ──────────────────────────────────────────────
                Row(verticalAlignment = Alignment.Top) {
                    // App icon
                    Box(
                        modifier = Modifier
                            .size(66.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(app.accentColor.copy(alpha = 0.14f))
                            .border(1.dp, app.accentColor.copy(0.28f), RoundedCornerShape(18.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(app.iconEmoji, fontSize = 32.sp)
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        // Name + badges
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(app.name, color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            if (app.isTopChart) {
                                Box(
                                    Modifier.clip(RoundedCornerShape(5.dp)).background(Color(0xFFFFA657).copy(0.18f))
                                        .border(1.dp, Color(0xFFFFA657).copy(0.35f), RoundedCornerShape(5.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) { Text("TOP", color = Color(0xFFFFA657), fontSize = 9.sp, fontWeight = FontWeight.ExtraBold) }
                            }
                        }
                        Spacer(Modifier.height(2.dp))
                        Text(app.tagline, color = TextSecondary, fontSize = 12.sp)
                        Spacer(Modifier.height(8.dp))
                        // Rating + Downloads
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            RatingChip(app.rating, app.ratingCount)
                            DownloadsChip(app.downloads)
                            CategoryChip(app.category, app.accentColor)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // ── Description ───────────────────────────────────────────────
                Text(app.description, color = TextSecondary, fontSize = 13.sp, lineHeight = 21.sp)

                Spacer(Modifier.height(18.dp))

                // ── Key Features ──────────────────────────────────────────────
                Text("KEY FEATURES", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.height(10.dp))

                // 2-column feature list
                val featurePairs = app.features.chunked(2)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    featurePairs.forEach { pair ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            pair.forEach { feature ->
                                Row(
                                    Modifier.weight(1f),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text("✓", color = app.accentColor, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 1.dp))
                                    Text(feature, color = TextSecondary, fontSize = 12.sp, lineHeight = 17.sp)
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
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    // App metadata
                    Column {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            MetadataLabel("v${app.version}", isCode = true)
                            MetadataLabel(app.size)
                            MetadataLabel(app.minAndroid)
                        }
                        Spacer(Modifier.height(3.dp))
                        Text("Updated ${app.lastUpdated}", color = TextSecondary, fontSize = 11.sp)
                    }
                    // Play Store button
                    Button(
                        onClick = onPlayStoreClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = app.accentColor,
                            contentColor = Color(0xFF0D1117)
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text("Play Store →", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Small sub-composables
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun RatingChip(rating: String, count: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color(0xFFFFA657).copy(0.12f))
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text("★", color = Color(0xFFFFA657), fontSize = 11.sp)
        Text(rating, color = Color(0xFFFFA657), fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text("($count)", color = TextSecondary, fontSize = 10.sp)
    }
}

@Composable
private fun DownloadsChip(downloads: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(DarkSurfaceVariant)
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text("⬇", color = TextSecondary, fontSize = 10.sp)
        Text(downloads, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun CategoryChip(category: String, accent: Color) {
    Box(
        Modifier.clip(RoundedCornerShape(6.dp)).background(accent.copy(0.13f))
            .padding(horizontal = 7.dp, vertical = 3.dp)
    ) {
        Text(category, color = accent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MetadataLabel(text: String, isCode: Boolean = false) {
    Text(
        text = text,
        color = TextSecondary,
        fontSize = 11.sp,
        fontFamily = if (isCode) FontFamily.Monospace else null
    )
}

