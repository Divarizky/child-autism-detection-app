package com.application.divarizky.autismdetection.ui.screens.autismdetection

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.divarizky.autismdetection.data.model.TFLiteModel
import com.application.divarizky.autismdetection.data.repository.UserRepository
import com.application.divarizky.autismdetection.utils.ImageHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Enum untuk status hasil deteksi
enum class DetectionResult {
    AUTISTIC, NON_AUTISTIC, SCAN_FAILED, FAILED
}

class AutismViewModel(
    private val userRepository: UserRepository,
    val context: Context
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val showDialog = mutableStateOf(false)
    val detectionResult = mutableStateOf<DetectionResult?>(null)
    val showPredictionDialog = mutableStateOf(false)
    private var imageBitmap: Bitmap? = null
    private val permissionGranted = mutableStateOf(false)

    private val tfliteModel: TFLiteModel? = try {
        TFLiteModel(context)
    } catch (e: Exception) {
        Log.e("AutismViewModel", "Error loading TFLiteModel: ${e.message}")
        null
    }

    fun onPermissionsGranted() {
        showDialog.value = true
    }

    fun onDialogDismiss() {
        showDialog.value = false
    }

    fun dismissPredictionDialog() {
        showPredictionDialog.value = false
    }

    fun handlePermissionResult(granted: Boolean) {
        permissionGranted.value = granted
    }

    fun handleImageResult(data: Intent?) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                // If pictures selected from gallery
                data?.data?.let { uri ->
                    val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
                    imageBitmap = bitmap
                    Log.d("AutismViewModel", "Foto berhasil dimuat dari galeri.")
                } ?: run {
                    // If picture taken from camera
                    ImageHandler.getTempImageFile()?.let { file ->
                        if (file.exists()) {
                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                            imageBitmap = bitmap
                            Log.d("AutismViewModel", "Foto berhasil dimuat dari: ${file.absolutePath}")
                        } else {
                            Log.e("AutismViewModel", "File foto tidak tersedia.")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("AutismViewModel", "Error dalam mengelola hasil foto: ${e.message}")
                detectionResult.value = DetectionResult.FAILED
                showPredictionDialog.value = true
            }
        }
    }

    fun runModelPrediction() {
        imageBitmap?.let { bitmap ->
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    isLoading.value = true
                    tfliteModel?.let { model ->
                        val prediction = model.predict(bitmap)
                        detectionResult.value = if (prediction >= 0.5) DetectionResult.AUTISTIC else DetectionResult.NON_AUTISTIC
                    } ?: run {
                        detectionResult.value = DetectionResult.SCAN_FAILED
                    }
                } catch (e: Exception) {
                    detectionResult.value = DetectionResult.SCAN_FAILED
                } finally {
                    isLoading.value = false
                    showPredictionDialog.value = true
                    ImageHandler.deleteTempImage()
                    Log.d("AutismViewModel", "Cache foto dihapus.")
                    imageBitmap = null
                }
            }
        } ?: run {
            detectionResult.value = DetectionResult.FAILED
            showPredictionDialog.value = true
        }
    }
}
