package com.application.divarizky.autismdetection.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonTextStyle

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonCornerRadius: Dp,
    buttonHeight: Dp,
    containerColor: Color,
    textStyle: TextStyle
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(buttonCornerRadius),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    CustomButton(
        text = "Login",
        onClick = {},
        buttonCornerRadius = 8.dp,
        buttonHeight = 50.dp,
        containerColor = Color.Blue,
        textStyle = buttonTextStyle
    )
}
