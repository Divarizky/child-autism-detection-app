package com.application.divarizky.autismdetection.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    onSecondary = White,


    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

object Dimensions {
    // Paddings
    val paddingStandard = 16.dp
    val horizontalPadding = 16.dp
    val verticalPadding = 20.dp

    // Image and Others Dimensions
    val imageSize = Modifier.size(width = 286.dp, height = 252.dp)
    val cornerRadius = 16.dp

    // Text Configuration
    val HeadlineStyle = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
    val titleTextStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
    val buttonTextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
    val contentFontSize = 16.sp
    val bodyFontSize = 12.sp

}

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