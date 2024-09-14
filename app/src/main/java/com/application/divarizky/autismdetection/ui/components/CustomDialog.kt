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
fun DialogFailed(
    showDialog: Boolean,
    onTryAgain: () -> Unit
){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onTryAgain,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Replace with appropriate icon for failure
                    // Example: Icon(painter = painterResource(id = R.drawable.failed_icon), contentDescription = null)
                    Text(
                        text = "❌", // Ini hanya contoh, ganti dengan ikon yang sesuai
                        textAlign = TextAlign.Center,
                        style = largeTextStyle,
                        fontWeight = FontWeight.Bold
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
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
    isAutismDetected: Boolean, // True jika autism terdeteksi, false jika non-autism
    onContinue: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onContinue,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Replace with appropriate icon for success
                    // Example: Icon(painter = painterResource(id = R.drawable.success_icon), contentDescription = null)
                    Text(
                        text = "✔️", // Ini hanya contoh, ganti dengan ikon yang sesuai
                        textAlign = TextAlign.Center,
                        style = largeTextStyle,
                        fontWeight = FontWeight.Bold
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
                        text = if (isAutismDetected) {
                            stringResource(id = R.string.autism_detected_message)
                        } else {
                            stringResource(id = R.string.non_autism_detected_message)
                        },
                        textAlign = TextAlign.Center,
                        style = regularTextStyle
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onContinue,
                    shape = RoundedCornerShape(buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
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
    onTryAgain: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onTryAgain,
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Replace with appropriate icon for failure
                    // Example: Icon(painter = painterResource(id = R.drawable.failed_icon), contentDescription = null)
                    Text(
                        text = "❌", // Ini hanya contoh, ganti dengan ikon yang sesuai
                        textAlign = TextAlign.Center,
                        style = largeTextStyle,
                        fontWeight = FontWeight.Bold
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
                        text = stringResource(id = R.string.scan_failed_message),
                        textAlign = TextAlign.Center,
                        style = regularTextStyle
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = onTryAgain,
                    shape = RoundedCornerShape(buttonCornerRadius),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
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
        isAutismDetected = true, // Change to false for non-autism
        onContinue = {}
    )
}

@Composable
@Preview(showBackground = true)
fun ResultDialogFailedPreview() {
    ResultDialogFailed(
        showDialog = true,
        onTryAgain = {}
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
