package com.application.divarizky.autismdetection.view.screens

import android.Manifest
import android.net.Uri
import android.os.Build
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.navigation.BottomNavigationBar
import com.application.divarizky.autismdetection.utils.ImageHandler
import com.application.divarizky.autismdetection.view.components.OptionDialog
import com.application.divarizky.autismdetection.view.components.ResultDialogFailed
import com.application.divarizky.autismdetection.view.components.ResultDialogSuccess
import com.application.divarizky.autismdetection.view.theme.Dimens
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonCornerRadius
import com.application.divarizky.autismdetection.view.theme.Dimens.buttonTextStyle
import com.application.divarizky.autismdetection.view.theme.Dimens.smallTextStyle
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.White
import com.application.divarizky.autismdetection.viewmodel.AutismViewModel
import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel
import com.application.divarizky.autismdetection.viewmodel.DetectionResult

@Composable
fun AutismDetectionScreen(
    viewModel: AutismViewModel,
    bottomNavbarViewModel: BottomNavbarViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val imageHandler = remember { ImageHandler() }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val galleryGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions[Manifest.permission.READ_MEDIA_IMAGES] ?: false
        } else {
            permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
        }

        viewModel.onPermissionResult(cameraGranted, galleryGranted)

        // Jika izin diberikan, langsung tampilkan OptionDialog
        if (cameraGranted && galleryGranted) {
            viewModel.showDialog.value = true
        }
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                ImageHandler.getTempImageFile()?.let { file ->
                    val photoUri = ImageHandler.getUriForFile(context, file)
                    viewModel.handleImageResult(photoUri)
                }
            }
        }
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            viewModel.handleImageResult(it)
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, bottomNavbarViewModel) }
    ) { innerPadding ->
        val scrollStateInternal = rememberScrollState(viewModel.scrollState.value)
        viewModel.updateScrollState(scrollStateInternal)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(Dimens.paddings)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollStateInternal)
            ) {
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.autism_detection_screen_title),
                        textAlign = TextAlign.Center,
                        style = Dimens.titleTextStyle,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                MainContent()
                Spacer(modifier = Modifier.weight(1f))
                ActionButtons(
                    onTakePicture = {
                        if (!viewModel.isPermissionGranted.value) {
                            cameraPermissionLauncher.launch(
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    arrayOf(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_MEDIA_IMAGES
                                    )
                                } else {
                                    arrayOf(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    )
                                }
                            )
                        } else {
                            viewModel.showDialog.value = true
                        }
                    },
                    onScan = {
                        viewModel.runModelPrediction(context)
                    },
                    viewModel = viewModel
                )
            }

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            DetectionResultDialog(viewModel)

            if (viewModel.showDialog.value) {
                OptionDialog(
                    showDialog = true,
                    onDismissRequest = viewModel::onDialogDismiss,
                    onTakePicture = {
                        viewModel.onDialogDismiss()
                        imageHandler.captureImage(context) { uri ->
                            uri?.let { takePictureLauncher.launch(it) }
                        }
                    },
                    onSelectFromGallery = {
                        viewModel.onDialogDismiss()
                        galleryLauncher.launch("image/*")
                    }
                )
            }
        }
    }
}

@Composable
fun MainContent() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_scan),
            contentDescription = stringResource(R.string.scan_image_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(Dimens.imageWidth)
                .height(Dimens.imageHeight)
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
    onScan: () -> Unit,
    viewModel: AutismViewModel
) {
    Column(
        modifier = Modifier.padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
            onClick = onTakePicture,
            enabled = !viewModel.isLoading.value
        ) {
            Text(
                text = stringResource(R.string.take_a_picture_button_text),
                style = buttonTextStyle,
                color = White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(buttonCornerRadius),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
            onClick = onScan,
            enabled = !viewModel.isLoading.value
        ) {
            Text(
                text = stringResource(R.string.scan_button_text),
                style = buttonTextStyle,
                color = White
            )
        }
    }
}

@Composable
fun DetectionResultDialog(viewModel: AutismViewModel) {
    if (viewModel.showPredictionDialog.value) {
        when (viewModel.detectionResult.value) {
            DetectionResult.AUTISTIC, DetectionResult.NON_AUTISTIC -> {
                ResultDialogSuccess(
                    showDialog = true,
                    isAutismDetected = viewModel.detectionResult.value == DetectionResult.AUTISTIC,
                    onContinue = { viewModel.dismissPredictionDialog() },
                    message = viewModel.predictionMessage.value
                )
            }

            DetectionResult.SCAN_FAILED, DetectionResult.FAILED -> {
                ResultDialogFailed(
                    showDialog = true,
                    onTryAgain = { viewModel.dismissPredictionDialog() },
                    message = viewModel.predictionMessage.value
                )
            }

            else -> Unit
        }
    }
}


/* KODE LAMA */

//import android.Manifest
//import android.content.Intent
//import android.provider.MediaStore
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.application.divarizky.autismdetection.R
//import com.application.divarizky.autismdetection.navigation.BottomNavigationBar
//import com.application.divarizky.autismdetection.utils.ImageHandler
//import com.application.divarizky.autismdetection.view.components.OptionDialog
//import com.application.divarizky.autismdetection.view.components.ResultDialogFailed
//import com.application.divarizky.autismdetection.view.components.ResultDialogSuccess
//import com.application.divarizky.autismdetection.view.theme.Dimens
//import com.application.divarizky.autismdetection.view.theme.Dimens.buttonCornerRadius
//import com.application.divarizky.autismdetection.view.theme.Dimens.buttonHeight
//import com.application.divarizky.autismdetection.view.theme.Dimens.buttonTextStyle
//import com.application.divarizky.autismdetection.view.theme.Dimens.smallTextStyle
//import com.application.divarizky.autismdetection.view.theme.MediumBlue
//import com.application.divarizky.autismdetection.view.theme.White
//import com.application.divarizky.autismdetection.viewmodel.AutismViewModel
//import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel
//import com.application.divarizky.autismdetection.viewmodel.DetectionResult
//
//@Composable
//fun AutismDetectionScreen(
//    viewModel: AutismViewModel,
//    bottomNavbarViewModel: BottomNavbarViewModel,
//    navController: NavController
//) {
//    val context = LocalContext.current
//    val imageHandler = remember { ImageHandler() }
//
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val cameraGranted = permissions[android.Manifest.permission.CAMERA] ?: false
//        val readStorageGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: false
//
//        if (cameraGranted && readStorageGranted) {
//            viewModel.onPermissionsGranted()
//        } else {
//            viewModel.handlePermissionResult(false)
//        }
//    }
//
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        viewModel.handleImageResult(result.data)
//    }
//
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartActivityForResult()
//    ) { result ->
//        viewModel.handleImageResult(result.data)
//    }
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(navController, bottomNavbarViewModel)
//        }
//    ) { innerPadding ->
//        val scrollStateInternal = rememberScrollState(viewModel.scrollState.value)
//        viewModel.updateScrollState(scrollStateInternal)
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(Dimens.paddings)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .verticalScroll(scrollStateInternal)
//            ) {
//                Header()
//                Spacer(modifier = Modifier.weight(1f))
//                ScanImage()
//                Spacer(modifier = Modifier.weight(1f))
//                ActionButtons(
//                    onTakePicture = {
//                        permissionLauncher.launch(
//                            arrayOf(
//                                Manifest.permission.CAMERA,
//                                Manifest.permission.READ_EXTERNAL_STORAGE
//                            )
//                        )
//                    },
//                    onSelectFromGallery = {
//                        imageHandler.selectImageFromGallery {
//                            galleryLauncher.launch(
//                                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                            )
//                        }
//                    },
//                    onCaptureImage = {
//                        imageHandler.captureImage(context) { uri ->
//                            uri?.let {
//                                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//                                    putExtra(MediaStore.EXTRA_OUTPUT, it)
//                                }
//                                cameraLauncher.launch(takePictureIntent)
//                            }
//                        }
//                    },
//                    onScan = {
//                        viewModel.runModelPrediction(context)
//                    },
//                    viewModel = viewModel
//                )
//            }
//
//            if (viewModel.isLoading.value) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//
//            DetectionResultDialog(viewModel)
//
//            OptionDialog(
//                showDialog = viewModel.showDialog.value,
//                onDismissRequest = viewModel::onDialogDismiss,
//                onTakePicture = {
//                    viewModel.onDialogDismiss()
//                    imageHandler.captureImage(context) { uri ->
//                        uri?.let {
//                            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
//                                putExtra(MediaStore.EXTRA_OUTPUT, it)
//                            }
//                            cameraLauncher.launch(takePictureIntent)
//                        }
//                    }
//                },
//                onSelectFromGallery = {
//                    viewModel.onDialogDismiss()
//                    galleryLauncher.launch(
//                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//                    )
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun Header() {
//    Box(
//        contentAlignment = Alignment.TopCenter,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = stringResource(R.string.autism_detection_screen_title),
//            textAlign = TextAlign.Center,
//            style = Dimens.titleTextStyle,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//@Composable
//fun ScanImage() {
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.img_scan),
//            contentDescription = stringResource(R.string.scan_image_description),
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .width(Dimens.imageWidth)
//                .height(Dimens.imageHeight)
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(
//            textAlign = TextAlign.Center,
//            text = stringResource(R.string.autism_detection_screen_support_text),
//            style = smallTextStyle
//        )
//    }
//}
//
//@Composable
//fun ActionButtons(
//    onTakePicture: () -> Unit,
//    onSelectFromGallery: () -> Unit,
//    onCaptureImage: () -> Unit,
//    onScan: () -> Unit,
//    viewModel: AutismViewModel
//) {
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier.padding(bottom = 16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Button(
//            onClick = onTakePicture,
//            shape = RoundedCornerShape(buttonCornerRadius),
//            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            enabled = !viewModel.isLoading.value
//        ) {
//            Text(
//                text = stringResource(R.string.take_a_picture_button_text),
//                style = buttonTextStyle,
//                color = White
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = { viewModel.runModelPrediction(context) },
//            shape = RoundedCornerShape(buttonCornerRadius),
//            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(buttonHeight),
//            enabled = !viewModel.isLoading.value
//        ) {
//            Text(
//                text = stringResource(R.string.scan_button_text),
//                style = buttonTextStyle,
//                color = White
//            )
//        }
//    }
//}
//
//@Composable
//fun DetectionResultDialog(viewModel: AutismViewModel) {
//    if (viewModel.showPredictionDialog.value) {
//        when (viewModel.detectionResult.value) {
//            DetectionResult.AUTISTIC, DetectionResult.NON_AUTISTIC -> {
//                ResultDialogSuccess(
//                    showDialog = true,
//                    isAutismDetected = viewModel.detectionResult.value == DetectionResult.AUTISTIC,
//                    onContinue = { viewModel.dismissPredictionDialog() },
//                    message = viewModel.predictionMessage.value
//                )
//            }
//            DetectionResult.SCAN_FAILED, DetectionResult.FAILED -> {
//                ResultDialogFailed(
//                    showDialog = true,
//                    onTryAgain = { viewModel.dismissPredictionDialog() },
//                    message = viewModel.predictionMessage.value
//                )
//            }
//            else -> Unit
//        }
//    }
//}
