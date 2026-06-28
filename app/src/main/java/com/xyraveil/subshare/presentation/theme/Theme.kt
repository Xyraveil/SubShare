package com.xyraveil.subshare.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = SubwayGreenLight,

    background = Color.White,
    surface = Color(0xFFF7F7F7),

    onBackground = Color.Black,
    onSurface = Color(0xFF1E1E1E),
    onPrimary = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = SubwayGreenDark,

    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),

    onBackground = Color.White,
    onSurface = Color(0xFF6A5D88),
    onPrimary = Color.White
)

@Composable
fun KawfeeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}