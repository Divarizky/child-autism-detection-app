package com.application.divarizky.autismdetection.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.divarizky.autismdetection.ui.theme.AutismDetectionTheme

@Composable
fun AutismDetectionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Check Your\nChild’s Autism Status",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.TopCenter),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(200.dp)
                .background(Color.LightGray, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for scan area
            Text(text = "Scan Area", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = {
                // scan logic
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Scan")
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AutismDetectionPreview() {
    AutismDetectionTheme {
        AutismDetectionScreen()
    }
}
