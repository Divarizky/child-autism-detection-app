package com.application.divarizky.autismdetection.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.data.model.TFLiteModel
import com.application.divarizky.autismdetection.utils.ImageHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class DetectionResult {
    AUTISTIC, NON_AUTISTIC, SCAN_FAILED, FAILED
}

class AutismViewModel(
    val context: Context
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val showDialog = mutableStateOf(false)
    val detectionResult = mutableStateOf<DetectionResult?>(null)
    val predictionMessage = mutableStateOf("")
    val showPredictionDialog = mutableStateOf(false)
    val isPermissionGranted = mutableStateOf(false)

    private var imageBitmap: Bitmap? = null
    var scrollState: ScrollState = ScrollState(0)

    private val tfliteModel: TFLiteModel? = try {
        TFLiteModel(context)
    } catch (e: Exception) {
        Log.e("AutismViewModel", "Error loading TFLiteModel: ${e.message}")
        null
    }

    fun onDialogDismiss() {
        showDialog.value = false
    }

    fun dismissPredictionDialog() {
        showPredictionDialog.value = false
    }

    fun updateScrollState(newScrollState: ScrollState) {
        scrollState = newScrollState
    }

    /**
     * Fungsi ini akan dipanggil setelah izin diberikan
     */
    fun onPermissionResult(cameraGranted: Boolean, galleryGranted: Boolean) {
        isPermissionGranted.value = cameraGranted && galleryGranted
    }

    /**
     * Menangani hasil pengambilan gambar dari kamera atau galeri
     */
    fun handleImageResult(uri: Uri?) {
        uri?.let {
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageBitmap = bitmap
                    Log.d("AutismViewModel", "Foto berhasil dimuat dari: $uri")
                }
            } catch (e: Exception) {
                Log.e("AutismViewModel", "Gagal memuat gambar: ${e.message}")
                detectionResult.value = DetectionResult.FAILED
                showPredictionDialog.value = true
            }
        }
    }

    private fun getDetectionMessage(context: Context, prediction: Float): String {
        return if (prediction >= 0.5) {
            context.getString(R.string.autism_detected_message, "autism")
        } else {
            context.getString(R.string.non_autism_detected_message, "non-autism")
        }
    }

    /**
     * Menjalankan prediksi deteksi autisme menggunakan model TensorFlow Lite
     */
    fun runModelPrediction(context: Context) {
        imageBitmap?.let { bitmap ->
            viewModelScope.launch(Dispatchers.Default) {
                try {
                    isLoading.value = true
                    tfliteModel?.let { model ->
                        val prediction = model.predict(bitmap)

                        detectionResult.value =
                            if (prediction >= 0.5) DetectionResult.AUTISTIC else DetectionResult.NON_AUTISTIC

                        // Used getDetectionMessage function to show detection result message
                        val message = getDetectionMessage(context, prediction)
                        predictionMessage.value = message
                    } ?: run {
                        detectionResult.value = DetectionResult.SCAN_FAILED
                        predictionMessage.value = context.getString(R.string.scan_failed_message)
                    }
                } catch (e: Exception) {
                    detectionResult.value = DetectionResult.SCAN_FAILED
                    predictionMessage.value = context.getString(R.string.scan_failed_message)
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
            predictionMessage.value = context.getString(R.string.no_picture_selected)
            showPredictionDialog.value = true
        }
    }
}

//    fun runModelPrediction(context: Context) {
//        imageBitmap?.let { bitmap ->
//            viewModelScope.launch(Dispatchers.Default) {
//                try {
//                    isLoading.value = true
//                    tfliteModel?.let { model ->
//                        val prediction = model.predict(bitmap)
//                        detectionResult.value =
//                            if (prediction >= 0.5) DetectionResult.AUTISTIC else DetectionResult.NON_AUTISTIC
//                        predictionMessage.value =
//                            "Deteksi Autisme: ${String.format("%.2f", prediction)}"
//                    } ?: run {
//                        detectionResult.value = DetectionResult.SCAN_FAILED
//                        predictionMessage.value = context.getString(R.string.scan_failed_message)
//                    }
//                } finally {
//                    isLoading.value = false
//                    showPredictionDialog.value = true
//                    ImageHandler.deleteTempImage() // Hapus gambar setelah scan selesai
//                    imageBitmap = null
//                }
//            }
//        } ?: run {
//            detectionResult.value = DetectionResult.FAILED
//            predictionMessage.value = context.getString(R.string.no_picture_selected)
//            showPredictionDialog.value = true
//        }
//    }



/* KODE SEBELUMNYA */

//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.util.Log
//import androidx.compose.foundation.ScrollState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.application.divarizky.autismdetection.R
//import com.application.divarizky.autismdetection.data.model.TFLiteModel
//import com.application.divarizky.autismdetection.data.repository.UserRepository
//import com.application.divarizky.autismdetection.utils.ImageHandler
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//// Enum class for configure the detection result
//enum class DetectionResult {
//    AUTISTIC, NON_AUTISTIC, SCAN_FAILED, FAILED
//}
//
//class AutismViewModel(
////    private val userRepository: UserRepository,
//    val context: Context
//) : ViewModel() {
//
//    val isLoading = mutableStateOf(false)
//    val showDialog = mutableStateOf(false)
//    val detectionResult = mutableStateOf<DetectionResult?>(null)
//    val predictionMessage = mutableStateOf("")
//    val showPredictionDialog = mutableStateOf(false)
//    private var imageBitmap: Bitmap? = null
//    private val permissionGranted = mutableStateOf(false)
//
//    var scrollState: ScrollState by mutableStateOf(ScrollState(0))
//
//    private val tfliteModel: TFLiteModel? = try {
//        TFLiteModel(context)
//    } catch (e: Exception) {
//        Log.e("AutismViewModel", "Error loading TFLiteModel: ${e.message}")
//        null
//    }
//
//    fun onPermissionsGranted() {
//        showDialog.value = true
//    }
//
//    fun onDialogDismiss() {
//        showDialog.value = false
//    }
//
//    fun dismissPredictionDialog() {
//        showPredictionDialog.value = false
//    }
//
//    fun handlePermissionResult(granted: Boolean) {
//        permissionGranted.value = granted
//    }
//
//    fun updateScrollState(newScrollState: ScrollState) {
//        scrollState = newScrollState
//    }
//
//    fun handleImageResult(data: Intent?) {
//        viewModelScope.launch(Dispatchers.Default) {
//            try {
//                // If pictures selected from gallery
//                data?.data?.let { uri ->
//                    val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
//                    imageBitmap = bitmap
//                    Log.d("AutismViewModel", "Foto berhasil dimuat dari galeri.")
//                } ?: run {
//                    // If picture taken from camera
//                    ImageHandler.getTempImageFile()?.let { file ->
//                        if (file.exists()) {
//                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
//                            imageBitmap = bitmap
//                            Log.d("AutismViewModel", "Foto berhasil dimuat dari: ${file.absolutePath}")
//                        } else {
//                            Log.e("AutismViewModel", "File foto tidak tersedia.")
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("AutismViewModel", "Error dalam mengelola hasil foto: ${e.message}")
//                detectionResult.value = DetectionResult.FAILED
//                showPredictionDialog.value = true
//            }
//        }
//    }
//
//    /* KODE SEBELUMNYA */
////    private fun getDetectionMessage(context: Context, prediction: Float): String {
////        // Gunakan konstanta untuk nilai ambang deteksi
////        val detectionThreshold = 0.5
////
////        // Tentukan resource string berdasarkan ambang deteksi
////        val stringResId = if (prediction >= detectionThreshold) {
////            R.string.autism_detected_message
////        } else {
////            R.string.non_autism_detected_message
////        }
////
////        // Label yang digunakan tetap "autism"
////        val label = "autism"
////
////        // Ambil string dari resource dan format dengan label
////        return context.getString(stringResId, label)
////    }
//
//    private fun getDetectionMessage(context: Context, prediction: Float): String {
//        return if (prediction >= 0.5) {
//            context.getString(R.string.autism_detected_message, "autism")
//        } else {
//            context.getString(R.string.non_autism_detected_message, "non-autism")
//        }
//    }
//
//    fun runModelPrediction(context: Context) {
//        imageBitmap?.let { bitmap ->
//            viewModelScope.launch(Dispatchers.Default) {
//                try {
//                    isLoading.value = true
//                    tfliteModel?.let { model ->
//                        val prediction = model.predict(bitmap)
//
//                        // Tentukan hasil deteksi
//                        detectionResult.value = if (prediction >= 0.5) DetectionResult.AUTISTIC else DetectionResult.NON_AUTISTIC
//
//                        // Format probabilitas sebagai desimal
//                        val probability = String.format("%.2f", prediction) // Format desimal 2 tempat
//
//                        // Buat pesan yang mencakup probabilitas
//                        val message = getDetectionMessage(context, prediction)
//                            .plus("\nProbabilitas Deteksi Autisme: $probability")
//
//                        predictionMessage.value = message
//                    } ?: run {
//                        detectionResult.value = DetectionResult.SCAN_FAILED
//                        predictionMessage.value = context.getString(R.string.scan_failed_message)
//                    }
//                } catch (e: Exception) {
//                    detectionResult.value = DetectionResult.SCAN_FAILED
//                    predictionMessage.value = context.getString(R.string.scan_failed_message)
//                } finally {
//                    isLoading.value = false
//                    showPredictionDialog.value = true
//                    ImageHandler.deleteTempImage()
//                    Log.d("AutismViewModel", "Cache foto dihapus.")
//                    imageBitmap = null
//                }
//            }
//        } ?: run {
//            detectionResult.value = DetectionResult.FAILED
//            predictionMessage.value = context.getString(R.string.no_picture_selected)
//            showPredictionDialog.value = true
//        }
//    }
//
//    /* === KODE SEBELUMNYA TANPA PROBABILITAS === */
////    fun runModelPrediction(context: Context) {
////        imageBitmap?.let { bitmap ->
////            viewModelScope.launch(Dispatchers.Default) {
////                try {
////                    isLoading.value = true
////                    tfliteModel?.let { model ->
////                        val prediction = model.predict(bitmap)
////
////                        detectionResult.value = if (prediction >= 0.5) DetectionResult.AUTISTIC else DetectionResult.NON_AUTISTIC
////
////                        // Used getDetectionMessage function to show detection result message
////                        val message = getDetectionMessage(context, prediction)
////                        predictionMessage.value = message
////                    } ?: run {
////                        detectionResult.value = DetectionResult.SCAN_FAILED
////                        predictionMessage.value = context.getString(R.string.scan_failed_message)
////                    }
////                } catch (e: Exception) {
////                    detectionResult.value = DetectionResult.SCAN_FAILED
////                    predictionMessage.value = context.getString(R.string.scan_failed_message)
////                } finally {
////                    isLoading.value = false
////                    showPredictionDialog.value = true
////                    ImageHandler.deleteTempImage()
////                    Log.d("AutismViewModel", "Cache foto dihapus.")
////                    imageBitmap = null
////                }
////            }
////        } ?: run {
////            detectionResult.value = DetectionResult.FAILED
////            predictionMessage.value = context.getString(R.string.no_picture_selected)
////            showPredictionDialog.value = true
////        }
////    }
//}
