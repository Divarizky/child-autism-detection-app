package com.application.divarizky.autismdetection.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
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
import com.application.divarizky.autismdetection.ui.components.BottomNavbar
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.cornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.imageSize
import com.application.divarizky.autismdetection.ui.theme.Dimens.largeTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.paddings
import com.application.divarizky.autismdetection.ui.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.titleTextStyle
import com.application.divarizky.autismdetection.ui.theme.MediumBlue
import com.application.divarizky.autismdetection.ui.theme.White

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavbar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(color = MediumBlue)
                .fillMaxSize()
                .padding(innerPadding)
                .padding(paddings) // tambahan padding untuk isi home screen
        ) {
            HomeContent(navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavHostController) {
    Column {
        Spacer(modifier = Modifier.height(24.dp))
        GreetingSection()
        ImageSection()
        FeatureCard(navController)
    }
}

@Composable
fun GreetingSection() {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Hi, Divarizky",
            style = titleTextStyle,
            fontWeight = FontWeight.Bold,
            color = White
        )
        Text(
            text = stringResource(R.string.home_screen_tagline),
            style = regularTextStyle,
            color = White,
            modifier = Modifier.padding(end = 125.dp)
        )
    }
}

@Composable
fun ImageSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.img_healthcare),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(imageSize)
        )
    }
}

@Composable
fun FeatureCard(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .background(White)
            .height(300.dp)
            .padding(paddings)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.check_child_for_autism),
                textAlign = TextAlign.Center,
                style = largeTextStyle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 50.dp)
            )
            Spacer(modifier = Modifier.height(paddings))

            Text(
                text = stringResource(R.string.check_child_support_text),
                textAlign = TextAlign.Center,
                style = smallTextStyle
            )
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    navController.navigate("autism_detection_screen")
                },
                shape = RoundedCornerShape(buttonCornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Check Now",
                    style = buttonTextStyle,
                    color = White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
