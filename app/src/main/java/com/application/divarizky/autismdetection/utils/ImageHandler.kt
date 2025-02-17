package com.application.divarizky.autismdetection.utils

//import android.content.Context
//import android.net.Uri
//import androidx.core.content.FileProvider
//import java.io.File
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//class ImageHandler {
//
//    companion object {
//        private var tempImageFile: File? = null
//
//        // Function to delete the temporary image file
//        fun deleteTempImage() {
//            tempImageFile?.let {
//                if (it.exists()) {
//                    it.delete()
//                }
//            }
//        }
//
//        // Function to get the temporary image file
//        fun getTempImageFile(): File? {
//            return tempImageFile
//        }
//
//        // Function to create a temporary image file
//        private fun createImageFile(context: Context): File? {
//            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
//            val imageFileName = "JPEG_${timeStamp}_"
//            val storageDir = context.cacheDir // Using cache directory to store the temporary image file
//            return File.createTempFile(imageFileName, ".jpg", storageDir)
//        }
//
//        // Function to get URI for the file
//        private fun getUriForFile(context: Context, file: File): Uri {
//            return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
//        }
//    }
//
//    // Function for capturing an image
//    fun captureImage(context: Context, onImageCaptured: (Uri?) -> Unit) {
//        deleteTempImage()
//        tempImageFile = createImageFile(context)
//        tempImageFile?.let { file ->
//            val photoURI: Uri = getUriForFile(context, file)
//            onImageCaptured(photoURI)
//        }
//    }
//
//    // Function for selecting an image from the gallery
//    fun selectImageFromGallery(onImageSelected: (Uri?) -> Unit) {
//        deleteTempImage()
//        onImageSelected(null)
//    }
//}




import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageHandler {

    companion object {
        private var tempImageFile: File? = null

        // Fungsi untuk menghapus gambar sementara setelah scan selesai
        fun deleteTempImage() {
            tempImageFile?.let {
                if (it.exists()) {
                    it.delete()
                    tempImageFile = null
                    println("Gambar cache berhasil dihapus.")
                }
            }
        }

        // Fungsi untuk mendapatkan file gambar sementara
        fun getTempImageFile(): File? {
            return tempImageFile
        }

        // Fungsi untuk membuat file gambar sementara di cache
        private fun createImageFile(context: Context): File {
            deleteTempImage() // Hapus file lama sebelum membuat yang baru
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val imageFileName = "JPEG_${timeStamp}_"
            return File.createTempFile(imageFileName, ".jpg", context.cacheDir)
        }

        // Fungsi untuk mendapatkan URI file dari FileProvider
        fun getUriForFile(context: Context, file: File): Uri {
            return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        }
    }

    // Fungsi untuk menangkap gambar dari kamera
    fun captureImage(context: Context, onImageCaptured: (Uri?) -> Unit) {
        tempImageFile = createImageFile(context)
        val photoURI: Uri = getUriForFile(context, tempImageFile!!)
        onImageCaptured(photoURI)
    }

    // Fungsi untuk memilih gambar dari galeri
    fun selectImageFromGallery(onImageSelected: (Uri?) -> Unit) {
        deleteTempImage()
        onImageSelected(null)
    }
}
