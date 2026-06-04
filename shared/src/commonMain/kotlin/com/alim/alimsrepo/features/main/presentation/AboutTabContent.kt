@file:OptIn(ExperimentalLayoutApi::class)

package com.alim.alimsrepo.features.main.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alim.alimsrepo.core.data.DevProfile
import com.alim.alimsrepo.core.data.ExperienceEntry
import com.alim.alimsrepo.core.data.HourlyRate
import com.alim.alimsrepo.core.data.ProfileStat
import com.alim.alimsrepo.core.data.ServicePillar
import com.alim.alimsrepo.core.data.ServiceTier
import com.alim.alimsrepo.core.data.SkillCategory
import com.alim.alimsrepo.core.data.experienceEntries
import com.alim.alimsrepo.core.data.hourlyRates
import com.alim.alimsrepo.core.data.profileStats
import com.alim.alimsrepo.core.data.servicePillars
import com.alim.alimsrepo.core.data.serviceTiers
import com.alim.alimsrepo.core.data.skillCategories
import com.alim.alimsrepo.core.ui.theme.AccentCyan
import com.alim.alimsrepo.core.ui.theme.AccentGreen
import com.alim.alimsrepo.core.ui.theme.AccentPurple
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
internal fun AboutTabContent(padding: PaddingValues) {
    val uriHandler = LocalUriHandler.current

    // Staggered entrance animation
    val totalItems = 9 // hero, whatIDo, expHeader, exp0, exp1, skillsHeader, skills, servicesHeader + tiers…
    val visibleIndices = remember { mutableStateListOf<Int>() }
    LaunchedEffect(Unit) {
        for (i in 0 until 20) { // enough for all items
            delay(60L)
            visibleIndices.add(i)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // ── Hero card ─────────────────────────────────────────────────────────
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(0),
                enter = fadeIn(tween(500)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(600)
                )
            ) {
                HeroCard(
                    onGithubClick = { uriHandler.openUri(DevProfile.githubUrl) },
                    onEmailClick  = { uriHandler.openUri("mailto:${DevProfile.email}") }
                )
            }
        }

        // ── What I Do ─────────────────────────────────────────────────────────
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(1),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                WhatIDoSection()
            }
        }

        // ── Experience ────────────────────────────────────────────────────────
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(2),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                AboutSectionHeader("EXPERIENCE")
            }
        }
        itemsIndexed(experienceEntries) { index, entry ->
            AnimatedVisibility(
                visible = visibleIndices.contains(3 + index),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                Column {
                    if (index > 0) {
                        // Timeline connector line
                        Box(
                            Modifier
                                .padding(start = 24.dp)
                                .width(2.dp)
                                .height(24.dp)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            experienceEntries[index - 1].accentColor.copy(0.3f),
                                            entry.accentColor.copy(0.3f)
                                        )
                                    )
                                )
                        )
                    }
                    ExperienceCard(entry)
                }
            }
        }

        // ── Skills ────────────────────────────────────────────────────────────
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(5),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                AboutSectionHeader("SKILLS & EXPERTISE")
            }
        }
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(6),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                SkillsCard()
            }
        }

        // ── Services ──────────────────────────────────────────────────────────
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(7),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                AboutSectionHeader("SERVICES & PRICING")
            }
        }
        itemsIndexed(serviceTiers) { index, tier ->
            AnimatedVisibility(
                visible = visibleIndices.contains(8 + index),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                ServiceTierCard(tier)
            }
        }
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(11),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                HourlyRatesCard()
            }
        }

        // ── Contact CTA ───────────────────────────────────────────────────────
        item {
            AnimatedVisibility(
                visible = visibleIndices.contains(12),
                enter = fadeIn(tween(400)) + slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(500)
                )
            ) {
                ContactCtaCard(
                    onEmailClick  = { uriHandler.openUri("mailto:${DevProfile.email}") },
                    onGithubClick = { uriHandler.openUri(DevProfile.githubUrl) }
                )
            }
        }

        item { Spacer(Modifier.height(8.dp)) }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Hero Card — with ambient glow and animated counters
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun HeroCard(onGithubClick: () -> Unit, onEmailClick: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        // Ambient glow behind the card
        Box(
            Modifier
                .size(500.dp)
                .offset(x = (-100).dp, y = (-120).dp)
                .background(
                    Brush.radialGradient(
                        listOf(
                            AccentGreen.copy(0.06f),
                            AccentCyan.copy(0.03f),
                            Color.Transparent
                        )
                    )
                )
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = CardShapeLarge,
            colors = CardDefaults.cardColors(containerColor = DarkSurface),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Box(
                Modifier.fillMaxWidth().background(
                    Brush.verticalGradient(
                        listOf(AccentGreen.copy(alpha = 0.13f), DarkSurface)
                    )
                )
            ) {
                Column(Modifier.fillMaxWidth().padding(24.dp)) {

                    // Availability pill
                    Box(
                        Modifier
                            .clip(PillShape)
                            .background(AccentGreen.copy(0.12f))
                            .border(1.dp, AccentGreen.copy(0.25f), PillShape)
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(6.dp).background(AccentGreen, CircleShape))
                            Spacer(Modifier.width(7.dp))
                            Text(
                                DevProfile.availability,
                                color = AccentGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(Modifier.height(22.dp))

                    // Avatar + Name
                    Row(verticalAlignment = Alignment.Top) {
                        // Double-ring avatar
                        Box(contentAlignment = Alignment.Center) {
                            // Outer glow ring
                            Box(
                                Modifier
                                    .size(94.dp)
                                    .border(
                                        2.dp,
                                        Brush.linearGradient(listOf(AccentGreen, AccentCyan)),
                                        CircleShape
                                    )
                            )
                            // Inner avatar
                            Box(
                                Modifier
                                    .size(86.dp)
                                    .border(2.dp, Color.White.copy(0.12f), CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(AccentGreen, AccentCyan)
                                        ),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    DevProfile.name.first().toString(),
                                    color = Color.White,
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                        Spacer(Modifier.width(20.dp))
                        Column(Modifier.weight(1f).padding(top = 6.dp)) {
                            Text(
                                DevProfile.name,
                                color = TextPrimary,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                lineHeight = 34.sp,
                                letterSpacing = (-0.3).sp
                            )
                            Spacer(Modifier.height(3.dp))
                            Text(
                                DevProfile.title,
                                color = AccentGreen,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    Modifier
                                        .size(4.dp)
                                        .background(TextSecondary, CircleShape)
                                )
                                Text(
                                    DevProfile.location,
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Animated Stats — 4 boxes in a row
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        profileStats.forEach { stat ->
                            AnimatedStatBox(stat, Modifier.weight(1f))
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                    HorizontalDivider(color = DarkSurfaceVariant)
                    Spacer(Modifier.height(18.dp))

                    // Bio
                    Text(
                        DevProfile.bio,
                        color = TextSecondary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )

                    Spacer(Modifier.height(22.dp))

                    // CTAs
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onGithubClick,
                            modifier = Modifier.weight(1f).height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AccentGreen,
                                contentColor = Color(0xFF0D1117)
                            )
                        ) {
                            Text(
                                "</> GitHub",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        OutlinedButton(
                            onClick = onEmailClick,
                            modifier = Modifier.weight(1f).height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, AccentCyan.copy(alpha = 0.6f))
                        ) {
                            Text(
                                "@ Email",
                                color = AccentCyan,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Animated Stat Counter Box
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun AnimatedStatBox(stat: ProfileStat, modifier: Modifier = Modifier) {
    // Parse numeric value from stat string (e.g. "17K+" -> 17, "6" -> 6)
    val numericPart = stat.value.filter { it.isDigit() }
    val suffix = stat.value.removePrefix(numericPart)
    val targetValue = numericPart.toFloatOrNull() ?: 0f

    val animatedValue = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        delay(200)
        animatedValue.animateTo(
            targetValue = targetValue,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.verticalGradient(
                    listOf(ElevatedSurface, DarkSurfaceVariant)
                )
            )
            .border(1.dp, DarkSurfaceVariant, RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${animatedValue.value.toInt()}$suffix",
            color = AccentCyan,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = (-0.3).sp
        )
        Spacer(Modifier.height(2.dp))
        Text(
            stat.label,
            color = TextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            stat.sublabel,
            color = TextSecondary,
            fontSize = 9.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// What I Do — Vertical layout with glow icons
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun WhatIDoSection() {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AboutSectionHeader("WHAT I DO")
        servicePillars.forEach { pillar ->
            ServicePillarCardVertical(pillar)
        }
    }
}

@Composable
private fun ServicePillarCardVertical(pillar: ServicePillar) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(pillar.accentColor.copy(0.07f), Color.Transparent)
                    )
                )
                .border(
                    1.dp,
                    pillar.accentColor.copy(alpha = 0.15f),
                    RoundedCornerShape(18.dp)
                )
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with glow
            Box(contentAlignment = Alignment.Center) {
                // Glow behind
                Box(
                    Modifier
                        .size(56.dp)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    pillar.accentColor.copy(0.15f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                Box(
                    Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(pillar.accentColor.copy(0.12f))
                        .border(
                            1.dp,
                            pillar.accentColor.copy(0.2f),
                            RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(pillar.icon, fontSize = 24.sp)
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    pillar.title,
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    pillar.subtitle,
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Experience — with timeline connector
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ExperienceCard(entry: ExperienceEntry) {
    val isCurrent = entry.period.contains("Present")
    val infiniteTransition = rememberInfiniteTransition(label = "current_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(1500, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            // Accent strip
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                entry.accentColor,
                                entry.accentColor.copy(0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                // Period + current indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        Modifier
                            .clip(ChipShape)
                            .background(entry.accentColor.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            entry.period,
                            color = entry.accentColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                    }
                    if (isCurrent) {
                        Box(
                            Modifier
                                .clip(ChipShape)
                                .background(AccentGreen.copy(if (isCurrent) 0.15f * pulseAlpha else 0.15f))
                                .border(
                                    1.dp,
                                    AccentGreen.copy(if (isCurrent) 0.35f * pulseAlpha else 0.3f),
                                    ChipShape
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "CURRENT",
                                color = AccentGreen.copy(alpha = if (isCurrent) pulseAlpha else 1f),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.8.sp
                            )
                        }
                    }
                }
                Spacer(Modifier.height(14.dp))
                Text(
                    entry.title,
                    color = TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(2.dp))
                Text(entry.company, color = TextSecondary, fontSize = 13.sp)
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(Modifier.height(14.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    entry.highlights.forEach { point ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                "→",
                                color = entry.accentColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 1.dp)
                            )
                            Text(
                                point,
                                color = TextSecondary,
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Skills — with animated chip pop-in
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun SkillsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(20.dp)) {
            skillCategories.forEachIndexed { index, category ->
                if (index > 0) {
                    Spacer(Modifier.height(18.dp))
                    // Gradient divider
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        category.accent.copy(0.3f),
                                        DarkSurfaceVariant.copy(0.3f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    Spacer(Modifier.height(18.dp))
                }
                // Category label with colored dot
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(category.accent)
                    )
                    Text(
                        category.name,
                        color = category.accent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
                Spacer(Modifier.height(10.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    category.skills.forEach { skill ->
                        SkillChip(skill, category.accent)
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillChip(skill: String, accent: Color) {
    Box(
        Modifier
            .clip(ChipShape)
            .background(accent.copy(alpha = 0.10f))
            .border(1.dp, accent.copy(alpha = 0.2f), ChipShape)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            skill,
            color = accent.copy(alpha = 0.9f),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Service Tiers — with animated gradient border on popular
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ServiceTierCard(tier: ServiceTier) {
    val infiniteTransition = rememberInfiniteTransition(label = "border_${tier.title}")
    val borderAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(4000, easing = LinearEasing)
        ),
        label = "border_angle"
    )

    val cardModifier = if (tier.isPopular) {
        Modifier
            .fillMaxWidth()
            .drawBehind {
                rotate(borderAngle) {
                    drawRect(
                        Brush.linearGradient(
                            listOf(AccentPurple, AccentCyan, AccentPurple),
                            start = Offset.Zero,
                            end = Offset(size.width, size.height)
                        )
                    )
                }
            }
            .padding(1.5.dp) // border width
    } else {
        Modifier.fillMaxWidth()
    }

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .then(
                    if (!tier.isPopular)
                        Modifier.border(
                            1.dp,
                            DarkSurfaceVariant.copy(0.6f),
                            RoundedCornerShape(20.dp)
                        )
                    else Modifier
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
                                tier.accentColor,
                                tier.accentColor.copy(0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
            // Gradient background for popular
            Box(
                Modifier.fillMaxWidth().then(
                    if (tier.isPopular)
                        Modifier.background(
                            Brush.verticalGradient(
                                listOf(
                                    tier.accentColor.copy(0.06f),
                                    Color.Transparent
                                )
                            )
                        )
                    else Modifier
                )
            ) {
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    // Popular badge
                    if (tier.isPopular) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Box(
                                Modifier
                                    .clip(ChipShape)
                                    .background(tier.accentColor.copy(0.2f))
                                    .border(
                                        1.dp,
                                        tier.accentColor.copy(0.4f),
                                        ChipShape
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    "MOST POPULAR",
                                    color = tier.accentColor,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                    }

                    // Title + Price
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.Top
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                tier.title,
                                color = TextPrimary,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                tier.subtitle,
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                tier.price,
                                color = tier.accentColor,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-0.3).sp
                            )
                            Text(
                                tier.unit,
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(18.dp))
                    HorizontalDivider(color = DarkSurfaceVariant)
                    Spacer(Modifier.height(14.dp))

                    // Includes label
                    Text(
                        "INCLUDES",
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(10.dp))

                    // Includes list
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        tier.includes.forEach { item ->
                            Row(
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.linearGradient(
                                                listOf(
                                                    tier.accentColor.copy(0.2f),
                                                    tier.accentColor.copy(0.1f)
                                                )
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "✓",
                                        color = tier.accentColor,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    item,
                                    color = TextSecondary,
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(top = 1.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(18.dp))

                    // Get Started CTA
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (tier.isPopular)
                                tier.accentColor
                            else
                                tier.accentColor.copy(0.15f),
                            contentColor = if (tier.isPopular)
                                Color.White
                            else
                                tier.accentColor
                        )
                    ) {
                        Text(
                            "Get Started →",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Hourly Rates
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun HourlyRatesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.fillMaxWidth().padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(AccentGreen)
                )
                Text(
                    "HOURLY RATES",
                    color = AccentGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "Billed weekly · 10-hour minimum",
                color = TextSecondary,
                fontSize = 11.sp
            )
            Spacer(Modifier.height(16.dp))

            hourlyRates.forEachIndexed { index, rate ->
                if (index > 0) {
                    Spacer(Modifier.height(4.dp))
                    HorizontalDivider(color = DarkSurfaceVariant)
                    Spacer(Modifier.height(4.dp))
                }
                HourlyRateRow(rate)
            }
        }
    }
}

@Composable
private fun HourlyRateRow(rate: HourlyRate) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(rate.accentColor)
                )
                Text(
                    rate.title,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                if (rate.isHighlighted) {
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(rate.accentColor.copy(0.18f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            "POPULAR",
                            color = rate.accentColor,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Spacer(Modifier.height(2.dp))
            Text(rate.description, color = TextSecondary, fontSize = 12.sp)
        }
        Spacer(Modifier.width(12.dp))
        Text(
            rate.rate,
            color = rate.accentColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Contact CTA — Enhanced with shimmer gradient
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ContactCtaCard(onEmailClick: () -> Unit, onGithubClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "cta_shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            tween(3000, easing = LinearEasing)
        ),
        label = "shimmer"
    )

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
                        listOf(
                            AccentPurple.copy(alpha = 0.14f),
                            AccentCyan.copy(alpha = 0.06f),
                            DarkSurface
                        )
                    )
                )
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Transparent,
                            Color.White.copy(0.03f),
                            Color.Transparent
                        ),
                        startX = shimmerOffset * 600f,
                        endX = (shimmerOffset + 0.5f) * 600f
                    )
                )
        ) {
            Column(Modifier.fillMaxWidth().padding(24.dp)) {
                // Speech bubble icon replacement
                Box(
                    Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(AccentPurple.copy(0.2f), AccentCyan.copy(0.12f))
                            )
                        )
                        .border(
                            1.dp,
                            AccentPurple.copy(0.25f),
                            RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "{ }",
                        color = AccentPurple,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Ready to build something great?",
                    color = TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30.sp,
                    letterSpacing = (-0.3).sp
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "I'm open to new projects, freelance work, and interesting collaborations. " +
                        "Let's discuss what we can build together.",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
                Spacer(Modifier.height(22.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onEmailClick,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentPurple,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "@ Email Me",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    OutlinedButton(
                        onClick = onGithubClick,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, AccentPurple.copy(0.5f))
                    ) {
                        Text(
                            "</> GitHub",
                            color = AccentPurple,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(DevProfile.email, color = TextSecondary, fontSize = 12.sp)
                    Text("  ·  ", color = DarkSurfaceVariant, fontSize = 12.sp)
                    Text(
                        "github.com/Alims-Repo",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Shared helpers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
internal fun AboutSectionHeader(title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Pulsing dot
        Box(
            Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(AccentCyan)
        )
        Box(
            Modifier
                .width(3.dp)
                .height(16.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(AccentCyan)
        )
        Text(
            title,
            color = AccentCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.2.sp
        )
    }
}
