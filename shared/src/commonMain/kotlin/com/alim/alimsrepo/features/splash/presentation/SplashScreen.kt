package com.alim.alimsrepo.features.splash.presentation

import alimsrepo.shared.generated.resources.Res
import alimsrepo.shared.generated.resources.compose_multiplatform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alim.alimsrepo.app.AppScreens
import com.alim.alimsrepo.core.ui.theme.AccentCyan
import com.alim.alimsrepo.core.ui.theme.AccentPurple
import com.alim.alimsrepo.core.ui.theme.DarkBackground
import com.alim.alimsrepo.core.ui.theme.TextSecondary
import io.github.alimsrepo.navease.runtime.annotations.AutoRegister
import io.github.alimsrepo.navease.runtime.navigation.NavController
import io.github.alimsrepo.navease.runtime.presentation.ActivityScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@AutoRegister(startDestination = true)
class SplashScreen : ActivityScreen<AppScreens.Splash>() {

    @Composable
    override fun Content(
        navKey: AppScreens.Splash,
        navController: NavController
    ) {
        val scale = remember { Animatable(0.6f) }
        val alpha = remember { Animatable(0f) }
        val slideUp = remember { Animatable(40f) }

        // Infinite pulsing rings
        val infiniteTransition = rememberInfiniteTransition(label = "splash_pulse")
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

            delay(1800L)
            navController.navigate(AppScreens.Main, finish = true)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            DarkBackground,
                            Color(0xFF0A0F1A),
                            DarkBackground
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Radial glow overlay
            Box(
                modifier = Modifier
                    .size(400.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AccentCyan.copy(alpha = 0.07f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .scale(scale.value)
                    .alpha(alpha.value)
                    .padding(bottom = slideUp.value.dp)
            ) {
                // Logo with pulsing rings
                Box(
                    modifier = Modifier.size(220.dp),
                    contentAlignment = Alignment.Center
                ) {
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
                    // Logo container
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
                        Icon(
                            painter = painterResource(Res.drawable.compose_multiplatform),
                            contentDescription = "Logo",
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Alim's Repo",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 36.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Kotlin Multiplatform Libraries",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    fontSize = 16.sp,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(52.dp))

                // Animated loading dots
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