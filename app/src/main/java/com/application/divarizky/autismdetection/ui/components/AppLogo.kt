package com.application.divarizky.autismdetection.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.application.divarizky.autismdetection.R

@Composable
fun AppLogo(
    logoResourceId: Int,
    logoSize: Dp,
    text: String,
    textStyle: TextStyle,
    textColor: Color,
    spacing: Dp,
    contentDescription: String = stringResource(R.string.app_name)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = logoResourceId),
            contentDescription = contentDescription,
            modifier = Modifier.size(logoSize)
        )
        Spacer(modifier = Modifier.width(spacing))

        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}
