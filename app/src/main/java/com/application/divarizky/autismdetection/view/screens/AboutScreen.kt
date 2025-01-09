package com.application.divarizky.autismdetection.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.view.theme.Black
import com.application.divarizky.autismdetection.view.theme.Dimens.paddings
import com.application.divarizky.autismdetection.view.theme.Dimens.titleTextStyle
import com.application.divarizky.autismdetection.view.theme.MediumBlue

@Composable
fun AboutScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddings)
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddings)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back Arrow",
                tint = Black,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        navController.navigateUp()
                    }
            )
            Text(
                text = "About Us",
                style = titleTextStyle,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        // CARE Logo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "CARE Application Logo",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(125.dp)
            )
            Text(
                text = stringResource(id = R.string.app),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = MediumBlue
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CARE (Children's Autism Resource & Education) is here to help parents understand and care for their autistic children. Created by a team of parents, professionals, and autism researchers, CARE offers a variety of features to help parents:\n"
                    + "\n" + "1. Early detection of autism: Through the recognition of typical facial patterns displayed by individuals with autism, CARE can help parents detect signs of autism in their children early on.\n" +
                    "2. Emotion detection: CARE helps parents understand their children's emotions by recognizing their facial expressions. This can help parents build better communication with their children and provide appropriate support.\n"
                    + "\n" + "Our team consists of talented young people who are participating in technology programs and are highly dedicated to improving the lives of autistic children and their families.\n" +
                    "\n" +
                    "Download the CARE app today and start helping your child thrive.",
            modifier = Modifier
                .padding(paddings)
                .align(Alignment.Start)
        )
    }
}