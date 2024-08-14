package com.application.divarizky.autismdetection.ui.screens.home

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.navigation.NavigationRoutes
import com.application.divarizky.autismdetection.ui.components.BottomNavbar
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.cornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.height
import com.application.divarizky.autismdetection.ui.theme.Dimens.imageHeight
import com.application.divarizky.autismdetection.ui.theme.Dimens.imageWidth
import com.application.divarizky.autismdetection.ui.theme.Dimens.largeTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.paddings
import com.application.divarizky.autismdetection.ui.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.titleTextStyle
import com.application.divarizky.autismdetection.ui.theme.MediumBlue
import com.application.divarizky.autismdetection.ui.theme.White
import com.application.divarizky.autismdetection.navigation.RoutesViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    routesViewModel: RoutesViewModel = viewModel()
) {
    Scaffold(
        bottomBar = {
            BottomNavbar(navController, routesViewModel)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(color = MediumBlue)
                .fillMaxSize()
                .padding(innerPadding)
                .padding(paddings)
        ) {
            HomeContent(navController, routesViewModel)
        }
    }
}

@Composable
fun HomeContent(navController: NavHostController, routesViewModel: RoutesViewModel) {
    Column {
        Spacer(modifier = Modifier.height(height))
        GreetingSection()
        ImageSection()
        Spacer(modifier = Modifier.height(height))
        FeatureCard(navController, routesViewModel)
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
            color = White
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
            modifier = Modifier
                .width(imageWidth)
                .height(imageHeight)
        )
    }
}

@Composable
fun FeatureCard(navController: NavHostController, routesViewModel: RoutesViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(cornerRadius))
            .background(White)
            .height(250.dp)
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
                modifier = Modifier.padding(horizontal = 25.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.check_child_support_text),
                textAlign = TextAlign.Center,
                style = smallTextStyle
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    routesViewModel.onNavigationItemSelected(NavigationRoutes.Main.route)
                    navController.navigate(NavigationRoutes.Main.route)
                },
                shape = RoundedCornerShape(buttonCornerRadius),
                colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
                modifier = Modifier
                    .width(160.dp)
                    .height(48.dp)
                    .padding(start = 8.dp, end = 8.dp)
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
