package com.application.divarizky.autismdetection.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.height
import com.application.divarizky.autismdetection.view.theme.Dimens.imageSize
import com.application.divarizky.autismdetection.view.theme.Dimens.largeTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.paddings
import com.application.divarizky.autismdetection.view.theme.Dimens.regularTextStyle
import com.application.divarizky.autismdetection.view.theme.GreenColor
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.RedColor

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
                                text = stringResource(R.string.take_picture),
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
                                text = stringResource(R.string.select_from_gallery_button_text),
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
fun DialogFailed(
    showDialog: Boolean,
    onTryAgain: () -> Unit,
){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onTryAgain,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_failed),
                        contentDescription = "Failed Icon",
                        tint = RedColor,
                        modifier = Modifier.size(imageSize)
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.title_result_failed),
                        textAlign = TextAlign.Center,
                        style = largeTextStyle,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(height))
                    Text(
                        text = stringResource(id = R.string.no_picture_selected),
                        textAlign = TextAlign.Center,
                        style = regularTextStyle
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onTryAgain,
                    shape = RoundedCornerShape(buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.try_again_button_text),
                        textAlign = TextAlign.Center,
                        style = buttonTextStyle
                    )
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun ResultDialogSuccess(
    showDialog: Boolean,
    isAutismDetected: Boolean,
    onContinue: () -> Unit,
    message: String
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onContinue,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_success),
                        contentDescription = "Success Icon",
                        tint = GreenColor,
                        modifier = Modifier.size(imageSize)
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.title_result_successful),
                        textAlign = TextAlign.Center,
                        style = largeTextStyle,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(height))
                    Text(
                        text = message,
                        textAlign = TextAlign.Center,
                        style = regularTextStyle
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onContinue,
                    shape = RoundedCornerShape(buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = GreenColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.continue_button_text),
                        textAlign = TextAlign.Center,
                        style = buttonTextStyle
                    )
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun ResultDialogFailed(
    showDialog: Boolean,
    onTryAgain: () -> Unit,
    message: String
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onTryAgain,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_failed),
                        contentDescription = "Failed Icon",
                        tint = RedColor,
                        modifier = Modifier.size(imageSize)
                    )
                }
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.title_result_failed),
                        textAlign = TextAlign.Center,
                        style = largeTextStyle,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(height))
                    Text(
                        text = message,
                        textAlign = TextAlign.Center,
                        style = regularTextStyle
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onTryAgain,
                    shape = RoundedCornerShape(buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = RedColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.try_again_button_text),
                        textAlign = TextAlign.Center,
                        style = buttonTextStyle
                    )
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ResultDialogSuccessPreview() {
    ResultDialogSuccess(
        showDialog = true,
        isAutismDetected = true,
        onContinue = {},
        message = "This is a message"
    )
}

@Composable
@Preview(showBackground = true)
fun ResultDialogFailedPreview() {
    ResultDialogFailed(
        showDialog = true,
        onTryAgain = {},
        message = "This is a message"
    )
}

@Composable
@Preview(showBackground = true)
fun DialogFailedPreview() {
    DialogFailed(
        showDialog = true,
        onTryAgain = {}
    )
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
