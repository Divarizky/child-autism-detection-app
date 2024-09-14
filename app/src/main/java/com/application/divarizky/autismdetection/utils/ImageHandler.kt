package com.application.divarizky.autismdetection.utils

import android.content.Context
import android.net.Uri
import com.application.divarizky.autismdetection.utils.ImageTemporaryFile
import java.io.File

class ImageHandler {

    companion object {
        private var tempImageFile: File? = null

        // Hapus gambar sementara
        fun deleteTempImage() {
            tempImageFile?.let {
                if (it.exists()) {
                    it.delete()
                }
            }
        }

        // Simpan sementara gambar
        fun getTempImageFile(): File? {
            return tempImageFile
        }
    }

    // Fungsi untuk menangani capture gambar
    fun captureImage(context: Context, onImageCaptured: (Uri?) -> Unit) {
        deleteTempImage()
        tempImageFile = ImageTemporaryFile.createImageFile(context)
        tempImageFile?.let { file ->
            val photoURI: Uri = ImageTemporaryFile.getUriForFile(context, file)
            onImageCaptured(photoURI)
        }
    }

    // Fungsi untuk memilih gambar dari galeri
    fun selectImageFromGallery(onImageSelected: (Uri?) -> Unit) {
        deleteTempImage()
        onImageSelected(null)
    }
}
