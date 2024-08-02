package com.application.divarizky.autismdetection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.ui.theme.AutismDetectionTheme
import com.application.divarizky.autismdetection.ui.theme.LocalResponsiveConfig
import com.application.divarizky.autismdetection.ui.theme.MediumBlue
import com.application.divarizky.autismdetection.ui.theme.White

@Composable
fun WelcomeScreen(navController: NavHostController) {
    val responsiveConfig = LocalResponsiveConfig.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(responsiveConfig.paddingStandard)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_welcome),
                contentDescription = stringResource(R.string.welcome_image_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(responsiveConfig.imageSize)
            )
            Spacer(modifier = Modifier.height(responsiveConfig.verticalPadding * 4))
            Text(
                text = stringResource(R.string.welcome_heading),
                fontSize = responsiveConfig.titleTextStyle,
            )
            Spacer(modifier = Modifier.height(responsiveConfig.verticalPadding))
            Text(
                text = stringResource(R.string.welcome_subheading),
                textAlign = TextAlign.Center,
                fontSize = responsiveConfig.mediumFontSize,
            )
        }

        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()
                .padding(responsiveConfig.paddingStandard)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = MediumBlue,
                        shape = RoundedCornerShape(responsiveConfig.cornerRadius)
                    )
                    .clickable {
                        navController.navigate("autism_detection")
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    val configuration = LocalConfiguration.current
    AutismDetectionTheme(screenWidth = configuration.screenWidthDp.dp) {
        WelcomeScreen(navController = rememberNavController())
    }
}
