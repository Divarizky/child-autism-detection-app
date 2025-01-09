package com.application.divarizky.autismdetection.view.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Define dimensions default configuration for components
object Dimens {

    // Padding configuration
    val paddings = 16.dp
    val height = 20.dp

    // Text configuration
    val appNameTextStyle = TextStyle(fontFamily = NunitoSansFamily, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
    val titleTextStyle = TextStyle(fontFamily = NunitoSansFamily, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    val largeTextStyle = TextStyle(fontFamily = NunitoSansFamily, fontSize = 20.sp)
    val mediumTextStyle = TextStyle(fontFamily = NunitoSansFamily, fontSize = 18.sp)
    val regularTextStyle = TextStyle(fontFamily = NunitoSansFamily, fontSize = 16.sp)
    val smallTextStyle = TextStyle(fontFamily = NunitoSansFamily, fontSize = 12.sp)
    val buttonTextStyle = TextStyle(fontFamily = NunitoSansFamily, fontSize = 14.sp, fontWeight = FontWeight.Bold)

    // Button configuration
    val buttonCornerRadius = 8.dp
    val buttonSize = 52.dp
    val buttonHeight = 50.dp

    // Images configuration
    val imageSize = 50.dp
    val imageWidth = 270.dp
    val imageHeight = 180.dp
    val cornerRadius = 16.dp
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
    // Dynamic color is available on Android 12+
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
