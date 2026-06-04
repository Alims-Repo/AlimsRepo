package com.alim.alimsrepo.features.splash.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alim.alimsrepo.app.AppScreens
import com.alim.alimsrepo.core.ui.theme.AccentCyan
import com.alim.alimsrepo.core.ui.theme.AccentGreen
import com.alim.alimsrepo.core.ui.theme.AccentPurple
import com.alim.alimsrepo.core.ui.theme.DarkBackground
import com.alim.alimsrepo.core.ui.theme.TextSecondary
import io.github.alimsrepo.navease.runtime.annotations.AutoRegister
import io.github.alimsrepo.navease.runtime.navigation.NavEaseController
import io.github.alimsrepo.navease.runtime.screen.ActivityScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AutoRegister
class SplashScreen : ActivityScreen<AppScreens.Splash>() {

    @Composable
    override fun Content(
        navKey: AppScreens.Splash,
        navEaseController: NavEaseController
    ) {
        // ── Entry animations ───────────────────────────────────────────
        val scale = remember { Animatable(0.6f) }
        val alpha = remember { Animatable(0f) }
        val slideUp = remember { Animatable(40f) }

        // Staggered text animations
        val titleAlpha = remember { Animatable(0f) }
        val titleSlide = remember { Animatable(24f) }
        val subtitleAlpha = remember { Animatable(0f) }
        val subtitleSlide = remember { Animatable(24f) }

        // ── Infinite transition for pulsing / loading / shimmer ────────
        val infiniteTransition = rememberInfiniteTransition(label = "splash_pulse")

        // Pulsing rings
        val ring1Scale = infiniteTransition.animateFloat(
            initialValue = 1f, targetValue = 2f,
            animationSpec = infiniteRepeatable(tween(1600, easing = FastOutSlowInEasing), RepeatMode.Restart),
            label = "ring1Scale"
        )
        val ring1Alpha = infiniteTransition.animateFloat(
            initialValue = 0.5f, targetValue = 0f,
            animationSpec = infiniteRepeatable(tween(1600), RepeatMode.Restart),
            label = "ring1Alpha"
        )
        val ring2Scale = infiniteTransition.animateFloat(
            initialValue = 1f, targetValue = 1.7f,
            animationSpec = infiniteRepeatable(tween(1600, delayMillis = 500, easing = FastOutSlowInEasing), RepeatMode.Restart),
            label = "ring2Scale"
        )
        val ring2Alpha = infiniteTransition.animateFloat(
            initialValue = 0.35f, targetValue = 0f,
            animationSpec = infiniteRepeatable(tween(1600, delayMillis = 500), RepeatMode.Restart),
            label = "ring2Alpha"
        )

        // Loading dots
        val dot1Alpha = infiniteTransition.animateFloat(
            initialValue = 0.2f, targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(500, delayMillis = 0), RepeatMode.Reverse),
            label = "dot1"
        )
        val dot2Alpha = infiniteTransition.animateFloat(
            initialValue = 0.2f, targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(500, delayMillis = 160), RepeatMode.Reverse),
            label = "dot2"
        )
        val dot3Alpha = infiniteTransition.animateFloat(
            initialValue = 0.2f, targetValue = 1f,
            animationSpec = infiniteRepeatable(tween(500, delayMillis = 320), RepeatMode.Reverse),
            label = "dot3"
        )

        // ── Rotating glow halo ─────────────────────────────────────────
        val haloRotation = infiniteTransition.animateFloat(
            initialValue = 0f, targetValue = 360f,
            animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing), RepeatMode.Restart),
            label = "haloRotation"
        )

        // ── Mesh gradient drift (bottom-left purple glow drifts vertically) ─
        val meshDrift = infiniteTransition.animateFloat(
            initialValue = -20f, targetValue = 20f,
            animationSpec = infiniteRepeatable(tween(6000, easing = FastOutSlowInEasing), RepeatMode.Reverse),
            label = "meshDrift"
        )

        // ── Title shimmer sweep ────────────────────────────────────────
        val shimmerOffset = infiniteTransition.animateFloat(
            initialValue = -300f, targetValue = 600f,
            animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Restart),
            label = "shimmerOffset"
        )

        // ── Launch entry + navigation ──────────────────────────────────
        LaunchedEffect(Unit) {
            launch {
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
                )
            }
            launch {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 600, delayMillis = 100)
                )
            }
            launch {
                slideUp.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 700, delayMillis = 200)
                )
            }
            // Staggered text – title first
            launch {
                delay(400)
                launch {
                    titleAlpha.animateTo(1f, tween(500))
                }
                launch {
                    titleSlide.animateTo(0f, tween(500, easing = FastOutSlowInEasing))
                }
            }
            // Subtitle 300ms after title
            launch {
                delay(700)
                launch {
                    subtitleAlpha.animateTo(1f, tween(500))
                }
                launch {
                    subtitleSlide.animateTo(0f, tween(500, easing = FastOutSlowInEasing))
                }
            }

            delay(1800L)
            navEaseController.navigate(AppScreens.Main, finish = true)
        }

        // ══════════════════════════════════════════════════════════════
        //  ROOT CONTAINER
        // ══════════════════════════════════════════════════════════════
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground),
            contentAlignment = Alignment.Center
        ) {

            // ── Mesh gradient background layer ─────────────────────────
            // Top-right cyan glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentCyan.copy(alpha = 0.06f),
                                Color.Transparent
                            ),
                            center = Offset(Float.POSITIVE_INFINITY, 0f),
                            radius = 900f
                        )
                    )
            )
            // Bottom-left purple glow (drifts)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = meshDrift.value.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentPurple.copy(alpha = 0.05f),
                                Color.Transparent
                            ),
                            center = Offset(0f, Float.POSITIVE_INFINITY),
                            radius = 800f
                        )
                    )
            )
            // Center-bottom green glow
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentGreen.copy(alpha = 0.04f),
                                Color.Transparent
                            ),
                            center = Offset(Float.POSITIVE_INFINITY * 0.5f, Float.POSITIVE_INFINITY),
                            radius = 700f
                        )
                    )
            )

            // ── Main content column ────────────────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .scale(scale.value)
                    .alpha(alpha.value)
                    .padding(bottom = slideUp.value.dp)
            ) {

                // ── Logo with pulsing rings + rotating halo ────────────
                Box(
                    modifier = Modifier.size(280.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Rotating glow halo
                    Box(
                        modifier = Modifier
                            .size(280.dp)
                            .graphicsLayer { rotationZ = haloRotation.value }
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        AccentCyan.copy(alpha = 0.12f),
                                        AccentPurple.copy(alpha = 0.08f),
                                        Color.Transparent
                                    ),
                                    center = Offset(140f, 80f),
                                    radius = 360f
                                ),
                                CircleShape
                            )
                    )

                    // Outer pulsing ring
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .scale(ring1Scale.value)
                            .alpha(ring1Alpha.value)
                            .border(1.5.dp, AccentCyan, CircleShape)
                    )
                    // Inner pulsing ring
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .scale(ring2Scale.value)
                            .alpha(ring2Alpha.value)
                            .border(1.5.dp, AccentPurple, CircleShape)
                    )

                    // Logo container — branded 'A' monogram
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(AccentCyan, AccentPurple)
                                ),
                                shape = RoundedCornerShape(28.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "A",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = (-1).sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // ── Title with shimmer effect ──────────────────────────
                val shimmerBrush = Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        AccentCyan.copy(alpha = 0.9f),
                        Color.White
                    ),
                    start = Offset(shimmerOffset.value, 0f),
                    end = Offset(shimmerOffset.value + 200f, 0f)
                )

                Text(
                    text = "Alim's Repo",
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        brush = shimmerBrush
                    ),
                    modifier = Modifier
                        .graphicsLayer {
                            this.alpha = titleAlpha.value
                            translationY = titleSlide.value
                        }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ── Subtitle line 1 (slides up with title timing) ──────
                Text(
                    text = "Kotlin Multiplatform Libraries",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    fontSize = 16.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .graphicsLayer {
                            this.alpha = titleAlpha.value
                            translationY = titleSlide.value
                        }
                )

                Spacer(modifier = Modifier.height(4.dp))

                // ── Subtitle line 2 — fades in 300ms later ─────────────
                Text(
                    text = "Android & KMP Developer",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AccentCyan.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier
                        .graphicsLayer {
                            this.alpha = subtitleAlpha.value
                            translationY = subtitleSlide.value
                        }
                )

                Spacer(modifier = Modifier.height(52.dp))

                // ── Animated loading dots ──────────────────────────────
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .alpha(dot1Alpha.value)
                            .background(AccentCyan, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .alpha(dot2Alpha.value)
                            .background(AccentCyan.copy(alpha = 0.8f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .alpha(dot3Alpha.value)
                            .background(AccentPurple, CircleShape)
                    )
                }
            }
        }
    }
}