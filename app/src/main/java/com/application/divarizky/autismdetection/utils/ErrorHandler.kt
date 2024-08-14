package com.application.divarizky.autismdetection.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.concurrent.TimeoutException

class ErrorHandler {

    companion object {

        // Login error handling
        fun handleLoginError(context: Context, e: Exception) {
            val errorMessage: String = when (e) {
                is IllegalArgumentException -> {
                    Log.e("LoginError", "Input tidak valid: ${e.message}")
                    "Login failed. Please check your email and password."
                }

                is NullPointerException -> {
                    Log.e("LoginError", "Data pengguna tidak ditemukan: ${e.message}")
                    "Data pengguna tidak ditemukan. Silakan coba lagi."
                }

                is IOException -> {
                    Log.e("LoginError", "Masalah jaringan: ${e.message}")
                    "Gagal terhubung ke server. Periksa koneksi internet Anda."
                }

                is TimeoutException -> {
                    Log.e("LoginError", "Permintaan waktu habis: ${e.message}")
                    "Permintaan login melebihi batas waktu. Silakan coba lagi."
                }

                else -> {
                    Log.e("LoginError", "Terjadi kesalahan: ${e.message}")
                    "Terjadi kesalahan yang tidak diketahui. Silakan coba lagi."
                }
            }

            // Feedback message to user
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        // General error handling
        fun handleGeneralError(context: Context, e: Exception) {
            val errorMessage = when (e) {
                is IOException -> {
                    Log.e("GeneralError", "Masalah jaringan: ${e.message}")
                    "Gagal terhubung ke server. Periksa koneksi internet Anda."
                }

                else -> {
                    Log.e("GeneralError", "Terjadi kesalahan: ${e.message}")
                    "Terjadi kesalahan. Silakan coba lagi."
                }
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }

        // API error handling
        fun handleApiError(context: Context, errorCode: Int) {
            val errorMessage = when (errorCode) {
                400 -> "Permintaan tidak valid. Silakan coba lagi."
                401 -> "Akses tidak diizinkan. Silakan login ulang."
                403 -> "Akses ditolak. Anda tidak memiliki izin untuk tindakan ini."
                404 -> "Data tidak ditemukan. Silakan coba lagi."
                500 -> "Kesalahan server. Silakan coba lagi nanti."
                else -> "Terjadi kesalahan. Silakan coba lagi."
            }

            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}