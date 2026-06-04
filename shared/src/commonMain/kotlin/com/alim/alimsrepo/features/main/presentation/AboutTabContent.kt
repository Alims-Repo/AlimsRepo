@file:OptIn(ExperimentalLayoutApi::class)

package com.alim.alimsrepo.features.main.presentation

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.alim.alimsrepo.core.ui.theme.DarkBackground
import com.alim.alimsrepo.core.ui.theme.DarkSurface
import com.alim.alimsrepo.core.ui.theme.DarkSurfaceVariant
import com.alim.alimsrepo.core.ui.theme.TextPrimary
import com.alim.alimsrepo.core.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────────────────
// Entry point
// ─────────────────────────────────────────────────────────────────────────────

@Composable
internal fun AboutTabContent(padding: PaddingValues) {
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // ── Hero card ─────────────────────────────────────────────────────────
        item {
            HeroCard(
                onGithubClick = { uriHandler.openUri(DevProfile.githubUrl) },
                onEmailClick  = { uriHandler.openUri("mailto:${DevProfile.email}") }
            )
        }

        // ── What I Do ─────────────────────────────────────────────────────────
        item { WhatIDoRow() }

        // ── Experience ────────────────────────────────────────────────────────
        item { AboutSectionHeader("EXPERIENCE") }
        items(experienceEntries) { entry -> ExperienceCard(entry) }

        // ── Skills ────────────────────────────────────────────────────────────
        item { AboutSectionHeader("SKILLS & EXPERTISE") }
        item { SkillsCard() }

        // ── Services ──────────────────────────────────────────────────────────
        item { AboutSectionHeader("SERVICES & PRICING") }
        items(serviceTiers) { tier -> ServiceTierCard(tier) }
        item { HourlyRatesCard() }

        // ── Contact CTA ───────────────────────────────────────────────────────
        item {
            ContactCtaCard(
                onEmailClick  = { uriHandler.openUri("mailto:${DevProfile.email}") },
                onGithubClick = { uriHandler.openUri(DevProfile.githubUrl) }
            )
        }

        item { Spacer(Modifier.height(8.dp)) }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Hero Card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun HeroCard(onGithubClick: () -> Unit, onEmailClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            Modifier.fillMaxWidth().background(
                Brush.verticalGradient(listOf(AccentGreen.copy(alpha = 0.13f), DarkSurface))
            )
        ) {
            Column(Modifier.fillMaxWidth().padding(24.dp)) {

                // Availability pill
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(7.dp).background(AccentGreen, CircleShape))
                    Spacer(Modifier.width(7.dp))
                    Text(DevProfile.availability, color = AccentGreen, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(Modifier.height(20.dp))

                // Avatar + Name
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        Modifier.size(80.dp)
                            .background(Brush.linearGradient(listOf(AccentGreen, AccentCyan)), CircleShape)
                            .border(2.dp, Color.White.copy(alpha = 0.12f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(DevProfile.name.first().toString(), color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.ExtraBold)
                    }
                    Spacer(Modifier.width(20.dp))
                    Column(Modifier.weight(1f).padding(top = 4.dp)) {
                        Text(DevProfile.name, color = TextPrimary, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 32.sp)
                        Spacer(Modifier.height(2.dp))
                        Text(DevProfile.title, color = AccentGreen, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("📍", fontSize = 11.sp)
                            Text(DevProfile.location, color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Stats — 4 boxes in a row
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    profileStats.forEach { stat -> StatBox(stat, Modifier.weight(1f)) }
                }

                Spacer(Modifier.height(24.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(Modifier.height(18.dp))

                // Bio
                Text(DevProfile.bio, color = TextSecondary, fontSize = 14.sp, lineHeight = 22.sp)

                Spacer(Modifier.height(20.dp))

                // CTAs
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onGithubClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color(0xFF0D1117))
                    ) {
                        Text("⚙  GitHub", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 2.dp))
                    }
                    OutlinedButton(
                        onClick = onEmailClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, AccentCyan.copy(alpha = 0.6f))
                    ) {
                        Text("✉  Email", color = AccentCyan, fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun StatBox(stat: ProfileStat, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurfaceVariant)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stat.value, color = AccentCyan, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        Text(stat.label, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        Text(stat.sublabel, color = TextSecondary, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// What I Do
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun WhatIDoRow() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        servicePillars.forEach { pillar -> ServicePillarCard(pillar, Modifier.weight(1f)) }
    }
}

@Composable
private fun ServicePillarCard(pillar: ServicePillar, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
                .border(1.dp, pillar.accentColor.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(pillar.icon, fontSize = 26.sp)
            Spacer(Modifier.height(8.dp))
            Text(pillar.title, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(2.dp))
            Text(pillar.subtitle, color = TextSecondary, fontSize = 10.sp, maxLines = 2, lineHeight = 14.sp)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Experience
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ExperienceCard(entry: ExperienceEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            // Accent strip
            Box(Modifier.fillMaxWidth().height(3.dp).background(
                Brush.horizontalGradient(listOf(entry.accentColor, entry.accentColor.copy(0.3f), Color.Transparent))
            ))
            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                // Period + current indicator
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        Modifier.clip(RoundedCornerShape(6.dp)).background(entry.accentColor.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(entry.period, color = entry.accentColor, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace)
                    }
                    if (entry.period.contains("Present")) {
                        Box(
                            Modifier.clip(RoundedCornerShape(6.dp)).background(AccentGreen.copy(0.15f))
                                .border(1.dp, AccentGreen.copy(0.3f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("CURRENT", color = AccentGreen, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.8.sp)
                        }
                    }
                }
                Spacer(Modifier.height(14.dp))
                Text(entry.title, color = TextPrimary, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(2.dp))
                Text(entry.company, color = TextSecondary, fontSize = 13.sp)
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(Modifier.height(14.dp))
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    entry.highlights.forEach { point ->
                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text("→", color = entry.accentColor, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 1.dp))
                            Text(point, color = TextSecondary, fontSize = 13.sp, lineHeight = 20.sp, modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Skills
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
                    HorizontalDivider(color = DarkSurfaceVariant.copy(alpha = 0.5f))
                    Spacer(Modifier.height(18.dp))
                }
                // Category label
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(Modifier.size(3.dp, 14.dp).clip(RoundedCornerShape(2.dp)).background(category.accent))
                    Text(category.name, color = category.accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                }
                Spacer(Modifier.height(10.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    category.skills.forEach { skill -> SkillChip(skill, category.accent) }
                }
            }
        }
    }
}

@Composable
private fun SkillChip(skill: String, accent: Color) {
    Box(
        Modifier.clip(RoundedCornerShape(8.dp))
            .background(accent.copy(alpha = 0.10f))
            .border(1.dp, accent.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(skill, color = accent.copy(alpha = 0.9f), fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Service Tiers
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ServiceTierCard(tier: ServiceTier) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
                .border(1.dp, if (tier.isPopular) tier.accentColor.copy(0.45f) else DarkSurfaceVariant.copy(0.6f), RoundedCornerShape(20.dp))
        ) {
            // Top gradient strip
            Box(Modifier.fillMaxWidth().height(3.dp).background(
                Brush.horizontalGradient(listOf(tier.accentColor, tier.accentColor.copy(0.3f), Color.Transparent))
            ))
            Column(Modifier.fillMaxWidth().padding(20.dp)) {
                // Popular badge
                if (tier.isPopular) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        Box(
                            Modifier.clip(RoundedCornerShape(6.dp)).background(tier.accentColor.copy(0.2f))
                                .border(1.dp, tier.accentColor.copy(0.4f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("MOST POPULAR", color = tier.accentColor, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 0.8.sp)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }

                // Title + Price
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.Top) {
                    Column(Modifier.weight(1f)) {
                        Text(tier.title, color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(2.dp))
                        Text(tier.subtitle, color = TextSecondary, fontSize = 12.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(horizontalAlignment = Alignment.End) {
                        Text(tier.price, color = tier.accentColor, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                        Text(tier.unit, color = TextSecondary, fontSize = 11.sp)
                    }
                }

                Spacer(Modifier.height(18.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(Modifier.height(14.dp))

                // Includes label
                Text("INCLUDES", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(Modifier.height(10.dp))

                // Includes list
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    tier.includes.forEach { item ->
                        Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                Modifier.size(18.dp).clip(CircleShape).background(tier.accentColor.copy(0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("✓", color = tier.accentColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(item, color = TextSecondary, fontSize = 13.sp, lineHeight = 20.sp, modifier = Modifier.weight(1f).padding(top = 1.dp))
                        }
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
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.size(3.dp, 14.dp).clip(RoundedCornerShape(2.dp)).background(AccentGreen))
                Text("HOURLY RATES", color = AccentGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }
            Spacer(Modifier.height(4.dp))
            Text("Billed weekly · 10-hour minimum", color = TextSecondary, fontSize = 11.sp)
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
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.size(7.dp).clip(CircleShape).background(rate.accentColor))
                Text(rate.title, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                if (rate.isHighlighted) {
                    Box(Modifier.clip(RoundedCornerShape(4.dp)).background(rate.accentColor.copy(0.18f)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("POPULAR", color = rate.accentColor, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
            Spacer(Modifier.height(2.dp))
            Text(rate.description, color = TextSecondary, fontSize = 12.sp)
        }
        Spacer(Modifier.width(12.dp))
        Text(rate.rate, color = rate.accentColor, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Contact CTA
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun ContactCtaCard(onEmailClick: () -> Unit, onGithubClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(AccentPurple.copy(alpha = 0.12f), DarkSurface)))) {
            Column(Modifier.fillMaxWidth().padding(24.dp)) {
                Text("💬", fontSize = 32.sp)
                Spacer(Modifier.height(14.dp))
                Text("Ready to build something great?", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 26.sp)
                Spacer(Modifier.height(6.dp))
                Text(
                    "I'm open to new projects, freelance work, and interesting collaborations. " +
                        "Let's discuss what we can build together.",
                    color = TextSecondary, fontSize = 14.sp, lineHeight = 22.sp
                )
                Spacer(Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onEmailClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AccentPurple, contentColor = Color.White)
                    ) {
                        Text("✉  Email Me", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 2.dp))
                    }
                    OutlinedButton(
                        onClick = onGithubClick,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, AccentPurple.copy(0.5f))
                    ) {
                        Text("⚙  GitHub", color = AccentPurple, fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
                Spacer(Modifier.height(14.dp))
                HorizontalDivider(color = DarkSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Text(DevProfile.email, color = TextSecondary, fontSize = 12.sp)
                    Text("  ·  ", color = DarkSurfaceVariant, fontSize = 12.sp)
                    Text("github.com/Alims-Repo", color = TextSecondary, fontSize = 12.sp)
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
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(Modifier.width(3.dp).height(16.dp).clip(RoundedCornerShape(2.dp)).background(AccentCyan))
        Text(title, color = AccentCyan, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.2.sp)
    }
}

