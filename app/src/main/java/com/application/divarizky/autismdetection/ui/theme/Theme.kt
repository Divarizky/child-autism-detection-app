package com.application.divarizky.autismdetection.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

// Define ResponsiveConfig data class
data class ResponsiveConfig(
    val smallFontSize: TextUnit,
    val mediumFontSize: TextUnit,
    val largeFontSize: TextUnit,
    val buttonHeight: Dp,
    val buttonWidth: Dp,
    val imageSize: Dp,
    val paddingStandard: Dp,
    val verticalPadding: Dp,
    val titleTextStyle: TextUnit,
    val cornerRadius: Dp
)

// Function to get the responsive configuration based on screen width
fun getResponsiveConfig(screenWidth: Dp): ResponsiveConfig {
    return when {
        screenWidth < 360.dp -> { // Small screens (e.g., phones with small displays)
            ResponsiveConfig(
                smallFontSize = 12.sp,
                mediumFontSize = 14.sp,
                largeFontSize = 16.sp,
                buttonHeight = 40.dp,
                buttonWidth = 150.dp,
                imageSize = 100.dp,
                paddingStandard = 8.dp,
                verticalPadding = 4.dp,
                titleTextStyle = 18.sp,
                cornerRadius = 8.dp
            )
        }
        screenWidth < 480.dp -> { // Medium screens (e.g., average smartphones)
            ResponsiveConfig(
                smallFontSize = 14.sp,
                mediumFontSize = 16.sp,
                largeFontSize = 18.sp,
                buttonHeight = 48.dp,
                buttonWidth = 200.dp,
                imageSize = 150.dp,
                paddingStandard = 12.dp,
                verticalPadding = 8.dp,
                titleTextStyle = 20.sp,
                cornerRadius = 12.dp
            )
        }
        screenWidth < 600.dp -> { // Large screens (e.g., large smartphones, small tablets)
            ResponsiveConfig(
                smallFontSize = 16.sp,
                mediumFontSize = 18.sp,
                largeFontSize = 20.sp,
                buttonHeight = 56.dp,
                buttonWidth = 250.dp,
                imageSize = 200.dp,
                paddingStandard = 16.dp,
                verticalPadding = 12.dp,
                titleTextStyle = 24.sp,
                cornerRadius = 16.dp
            )
        }
        screenWidth < 720.dp -> { // Extra large screens (e.g., tablets)
            ResponsiveConfig(
                smallFontSize = 18.sp,
                mediumFontSize = 20.sp,
                largeFontSize = 22.sp,
                buttonHeight = 64.dp,
                buttonWidth = 300.dp,
                imageSize = 250.dp,
                paddingStandard = 20.dp,
                verticalPadding = 16.dp,
                titleTextStyle = 26.sp,
                cornerRadius = 20.dp
            )
        }
        else -> { // Extra extra large screens (e.g., large tablets, desktop emulators)
            ResponsiveConfig(
                smallFontSize = 20.sp,
                mediumFontSize = 22.sp,
                largeFontSize = 24.sp,
                buttonHeight = 72.dp,
                buttonWidth = 350.dp,
                imageSize = 300.dp,
                paddingStandard = 24.dp,
                verticalPadding = 20.dp,
                titleTextStyle = 28.sp,
                cornerRadius = 24.dp
            )
        }
    }
}

// Define a CompositionLocal to provide ResponsiveConfig
val LocalResponsiveConfig = staticCompositionLocalOf {
    ResponsiveConfig(
        smallFontSize = 16.sp,
        mediumFontSize = 18.sp,
        largeFontSize = 20.sp,
        buttonHeight = 56.dp,
        buttonWidth = 250.dp,
        imageSize = 200.dp,
        paddingStandard = 16.dp,
        verticalPadding = 12.dp,
        titleTextStyle = 24.sp,
        cornerRadius = 16.dp
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = MediumBlue,
    secondary = CornflowerBlue,
    onPrimary = Black,
    onSecondary = Black
)

private val LightColorScheme = lightColorScheme(
    primary = MediumBlue,
    secondary = CornflowerBlue,
    onPrimary = White,
    onSecondary = White
)

@Composable
fun AutismDetectionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    screenWidth: Dp,
    dynamicColor: Boolean = true,
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

    val responsiveConfig = getResponsiveConfig(screenWidth)

    CompositionLocalProvider(LocalResponsiveConfig provides responsiveConfig) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
