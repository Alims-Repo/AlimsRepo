package com.alim.alimsrepo.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Core Palette ─────────────────────────────────────────────────────────────

val DarkBackground = Color(0xFF0D1117)
val DarkSurface = Color(0xFF161B22)
val DarkSurfaceVariant = Color(0xFF21262D)
val ElevatedSurface = Color(0xFF1C2128)

val AccentCyan = Color(0xFF58A6FF)
val AccentGreen = Color(0xFF3FB950)
val AccentPurple = Color(0xFFA371F7)
val AccentOrange = Color(0xFFFFA657)
val AccentRed = Color(0xFFFF7B72)
val AccentGold = Color(0xFFE3B341)

val GlowCyan = AccentCyan.copy(alpha = 0.10f)
val GlowPurple = AccentPurple.copy(alpha = 0.10f)
val GlowGreen = AccentGreen.copy(alpha = 0.10f)

val TextPrimary = Color(0xFFE6EDF3)
val TextSecondary = Color(0xFF8B949E)

// ─── Shape Constants ──────────────────────────────────────────────────────────

val CardShapeLarge = RoundedCornerShape(24.dp)
val CardShapeMedium = RoundedCornerShape(16.dp)
val CardShapeSmall = RoundedCornerShape(12.dp)
val ChipShape = RoundedCornerShape(8.dp)
val PillShape = RoundedCornerShape(50)

// ─── Typography ───────────────────────────────────────────────────────────────

private val AlimsTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.3).sp
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.8.sp
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 9.sp,
        lineHeight = 14.sp,
        letterSpacing = 1.sp
    )
)

// ─── Color Scheme ─────────────────────────────────────────────────────────────

private val DarkColorScheme = darkColorScheme(
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    primary = AccentCyan,
    secondary = AccentGreen,
    tertiary = AccentPurple,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
)

@Composable
fun AlimsRepoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AlimsTypography,
        content = content
    )
}