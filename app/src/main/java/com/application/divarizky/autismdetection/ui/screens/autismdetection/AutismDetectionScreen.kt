package com.application.divarizky.autismdetection.ui.screens.autismdetection

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.application.divarizky.autismdetection.MyApp
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.navigation.RoutesViewModel
import com.application.divarizky.autismdetection.ui.components.BottomNavbar
import com.application.divarizky.autismdetection.ui.components.DialogFailed
import com.application.divarizky.autismdetection.ui.components.OptionDialog
import com.application.divarizky.autismdetection.ui.components.ResultDialogFailed
import com.application.divarizky.autismdetection.ui.components.ResultDialogSuccess
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonHeight
import com.application.divarizky.autismdetection.ui.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.imageHeight
import com.application.divarizky.autismdetection.ui.theme.Dimens.imageWidth
import com.application.divarizky.autismdetection.ui.theme.Dimens.paddings
import com.application.divarizky.autismdetection.ui.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.ui.theme.Dimens.titleTextStyle
import com.application.divarizky.autismdetection.ui.theme.MediumBlue
import com.application.divarizky.autismdetection.utils.ImageHandler
import com.application.divarizky.autismdetection.utils.viewModelFactory

@Composable
fun AutismDetectionScreen(
    navController: NavHostController,
    routesViewModel: RoutesViewModel = viewModel(),
    viewModel: AutismViewModel = viewModel(factory = viewModelFactory {
        AutismViewModel(MyApp.appModule.userRepository, MyApp.appModule.context)
    })
) {
    val context = LocalContext.current
    val imageHandler = remember { ImageHandler() }

    // Permissions for camera and read storage (gallery)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[android.Manifest.permission.CAMERA] ?: false
        val readStorageGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

        // If permissions is granted and open option dialog
        if (cameraGranted && readStorageGranted) {
            viewModel.onPermissionsGranted()
        } else {
            // If permissions is not granted
            viewModel.handlePermissionResult(false)
        }
    }

    // Launchers for camera and gallery
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.handleImageResult(result.data)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        viewModel.handleImageResult(result.data)
    }

    Scaffold(
        bottomBar = {
            BottomNavbar(navController, routesViewModel)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(paddings)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Header()
                Spacer(modifier = Modifier.weight(1f))
                ScanImage()
                Spacer(modifier = Modifier.weight(1f))
                ActionButtons(
                    onTakePicture = {
                        permissionLauncher.launch(
                            arrayOf(
                                android.Manifest.permission.CAMERA,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        )
                    },
                    onSelectFromGallery = {
                        imageHandler.selectImageFromGallery {
                            galleryLauncher.launch(
                                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            )
                        }
                    },
                    onCaptureImage = {
                        imageHandler.captureImage(context) { uri ->
                            uri?.let {
                                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                                    putExtra(MediaStore.EXTRA_OUTPUT, it)
                                }
                                cameraLauncher.launch(takePictureIntent)
                            }
                        }
                    },
                    onScan = {
                        viewModel.runModelPrediction()
                    },
                    viewModel = viewModel
                )
            }

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (viewModel.showPredictionDialog.value) {
                when (viewModel.detectionResult.value) {
                    DetectionResult.AUTISTIC -> {
                        ResultDialogSuccess(
                            showDialog = true,
                            isAutismDetected = true,
                            onContinue = { viewModel.dismissPredictionDialog() }
                        )
                    }
                    DetectionResult.NON_AUTISTIC -> {
                        ResultDialogSuccess(
                            showDialog = true,
                            isAutismDetected = false,
                            onContinue = { viewModel.dismissPredictionDialog() }
                        )
                    }
                    DetectionResult.SCAN_FAILED -> {
                        ResultDialogFailed(
                            showDialog = true,
                            onTryAgain = { viewModel.dismissPredictionDialog() }
                        )
                    }
                    DetectionResult.FAILED -> {
                        DialogFailed(
                            showDialog = true,
                            onTryAgain = { viewModel.dismissPredictionDialog() }
                        )
                    }
                    else -> Unit
                }
            }

            // Show OptionDialog if permissions are granted
            OptionDialog(
                showDialog = viewModel.showDialog.value,
                onDismissRequest = viewModel::onDialogDismiss,
                onTakePicture = {
                    viewModel.onDialogDismiss()
                    imageHandler.captureImage(context) { uri ->
                        uri?.let {
                            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                                putExtra(MediaStore.EXTRA_OUTPUT, it)
                            }
                            cameraLauncher.launch(takePictureIntent)
                        }
                    }
                },
                onSelectFromGallery = {
                    viewModel.onDialogDismiss()
                    galleryLauncher.launch(
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    )
                }
            )
        }
    }
}

@Composable
fun Header() {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.autism_detection_screen_title),
            textAlign = TextAlign.Center,
            style = titleTextStyle,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ScanImage() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_scan),
            contentDescription = stringResource(R.string.scan_image_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(imageWidth)
                .height(imageHeight)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            textAlign = TextAlign.Center,
            text = stringResource(R.string.autism_detection_screen_support_text),
            style = smallTextStyle
        )
    }
}

@Composable
fun ActionButtons(
    onTakePicture: () -> Unit,
    onSelectFromGallery: () -> Unit,
    onCaptureImage: () -> Unit,
    onScan: () -> Unit,
    viewModel: AutismViewModel
) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onTakePicture,
            shape = RoundedCornerShape(buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !viewModel.isLoading.value
        ) {
            Text(
                text = stringResource(R.string.take_a_picture_button_text),
                style = buttonTextStyle
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onScan,
            shape = RoundedCornerShape(buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight),
            enabled = !viewModel.isLoading.value
        ) {
            Text(
                text = stringResource(R.string.scan_button_text),
                style = buttonTextStyle
            )
        }
    }
}
