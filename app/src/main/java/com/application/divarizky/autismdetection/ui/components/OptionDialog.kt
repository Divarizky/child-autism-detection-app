package com.application.divarizky.autismdetection.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.height
import com.application.divarizky.autismdetection.ui.theme.Dimens.largeTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.paddings
import com.application.divarizky.autismdetection.ui.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.ui.theme.MediumBlue

@Composable
fun OptionDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onTakePicture: () -> Unit,
    onSelectFromGallery: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.option_dialog_title),
                        textAlign = TextAlign.Center,
                        style = largeTextStyle,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.option_dialog_description),
                        textAlign = TextAlign.Center,
                        style = regularTextStyle
                    )
                    Spacer(modifier = Modifier.height(height))

                    // set button layout
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(paddings),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                onDismissRequest()
                                onTakePicture()
                            },
                            shape = RoundedCornerShape(buttonCornerRadius),
                            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(R.string.option_dialog_take_picture),
                                textAlign = TextAlign.Center,
                                style = buttonTextStyle
                            )
                        }
                        Button(
                            onClick = {
                                onDismissRequest()
                                onSelectFromGallery()
                            },
                            shape = RoundedCornerShape(buttonCornerRadius),
                            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(R.string.option_dialog_select_from_gallery),
                                textAlign = TextAlign.Center,
                                style = buttonTextStyle
                            )
                        }
                    }
                }
            },
            confirmButton = {
                /* not used in this case */
            },
            containerColor = Color.White
        )
    }
}

@Composable
@Preview(showBackground = true)
fun OptionDialogPreview() {
    OptionDialog(
        showDialog = true,
        onDismissRequest = {},
        onTakePicture = {},
        onSelectFromGallery = {}
    )
}
