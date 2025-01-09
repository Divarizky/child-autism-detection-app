package com.application.divarizky.autismdetection.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.view.theme.Dimens.height
import com.application.divarizky.autismdetection.view.theme.Dimens.imageHeight
import com.application.divarizky.autismdetection.view.theme.Dimens.imageWidth
import com.application.divarizky.autismdetection.view.theme.Dimens.paddings
import com.application.divarizky.autismdetection.view.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.titleTextStyle
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.White

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ContentSection()
        BottomButton(navController)
    }
}

@Composable
fun ContentSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(paddings)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_welcome),
            contentDescription = stringResource(R.string.welcome_image_description),
            modifier = Modifier
                .width(imageWidth)
                .height(imageHeight)
        )
        Spacer(modifier = Modifier.height(height))

        Text(
            text = stringResource(R.string.welcome_heading),
            textAlign = TextAlign.Center,
            style = titleTextStyle,
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = stringResource(R.string.welcome_subheading),
            textAlign = TextAlign.Center,
            style = regularTextStyle,
        )
    }
}

@Composable
fun BottomButton(navController: NavController) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(
                    color = MediumBlue,
                    shape = RoundedCornerShape(buttonCornerRadius)
                )
                .clickable {
                    navController.navigate("login_screen")
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = stringResource(R.string.navigate_next),
                tint = White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navController = rememberNavController())
}
