package com.application.divarizky.autismdetection.data.model

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class TFLiteModel(context: Context) {
    private val interpreter: Interpreter

    init {
        // Memuat model TensorFlow Lite dari folder assets
        val assetFileDescriptor = context.assets.openFd("autism_model.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val model = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        interpreter = Interpreter(model)
    }

    fun predict(bitmap: Bitmap): Float {
        // Mengubah Bitmap menjadi ByteBuffer
        val input = convertBitmapToByteBuffer(bitmap)

        // Output dari model adalah [1, OUTPUT_CLASSES], kita perlu mengekstraksi array internal
        val output = Array(1) { FloatArray(OUTPUT_CLASSES) }
        interpreter.run(input, output)

        // Mengambil nilai probabilitas tertinggi dari output
        return output[0].maxOrNull() ?: 0.0f
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val inputSize = 224 // Ukuran input yang diharapkan oleh model
        val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true)
        val intValues = IntArray(inputSize * inputSize)
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)

        var pixel = 0
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val value = intValues[pixel++]
                val r = (value shr 16 and 0xFF) / 255.0f
                val g = (value shr 8 and 0xFF) / 255.0f
                val b = (value and 0xFF) / 255.0f

                // Normalisasi dari [0, 1] ke [-1, 1]
                byteBuffer.putFloat((r - 0.5f) * 2)
                byteBuffer.putFloat((g - 0.5f) * 2)
                byteBuffer.putFloat((b - 0.5f) * 2)
            }
        }
        return byteBuffer
    }

    companion object {
        private const val OUTPUT_CLASSES = 1000 // Jumlah kelas output sesuai dengan model Anda
    }
}
