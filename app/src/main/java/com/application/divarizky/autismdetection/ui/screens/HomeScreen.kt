package com.application.divarizky.autismdetection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.ui.theme.LocalResponsiveConfig
import com.application.divarizky.autismdetection.ui.theme.MediumBlue
import com.application.divarizky.autismdetection.ui.theme.ResponsiveConfig
import com.application.divarizky.autismdetection.ui.theme.White

@Composable
fun HomeScreen(navController: NavHostController) {
    HomeContent(navController)
}

@Composable
fun HomeContent(navController: NavHostController) {
    val responsiveConfig = LocalResponsiveConfig.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MediumBlue)
            .padding(responsiveConfig.paddingStandard)
    ) {
        Spacer(modifier = Modifier.height(responsiveConfig.verticalPadding))

        GreetingSection(responsiveConfig)

        Spacer(modifier = Modifier.height(responsiveConfig.verticalPadding))

        ImageSection()

        Spacer(modifier = Modifier.height(24.dp))

        FeatureCard(navController, responsiveConfig)
    }
}

@Composable
fun GreetingSection(responsiveConfig: ResponsiveConfig) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Hi, Divarizky",
            fontSize = responsiveConfig.titleTextStyle,
            fontWeight = FontWeight.Bold,
            color = White
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.home_screen_tagline),
            fontSize = responsiveConfig.mediumFontSize,
            color = White
        )
    }
}

@Composable
fun ImageSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.img_healthcare),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
        )
    }
}

@Composable
fun FeatureCard(navController: NavHostController, responsiveConfig: ResponsiveConfig) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(responsiveConfig.cornerRadius))
            .background(White)
            .height(300.dp)
            .padding(
                vertical = responsiveConfig.paddingStandard,
                horizontal = responsiveConfig.paddingStandard
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.home_check_child_for_autism),
                textAlign = TextAlign.Center,
                fontSize = responsiveConfig.mediumFontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 28.dp, start = 50.dp, end = 50.dp)
            )
            Spacer(modifier = Modifier.height(responsiveConfig.verticalPadding))

            Text(
                text = stringResource(R.string.home_check_child_support_text),
                textAlign = TextAlign.Center,
                fontSize = responsiveConfig.smallFontSize,
            )
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    navController.navigate("autism_detection_screen")
                },
                shape = RoundedCornerShape(responsiveConfig.cornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
                modifier = Modifier
                    .width(responsiveConfig.buttonWidth)
                    .height(responsiveConfig.buttonHeight)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Check Now", color = White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
