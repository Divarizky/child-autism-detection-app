package com.application.divarizky.autismdetection.view.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <VM : ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
    println("viewModelFactory dipanggil")
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return initializer() as T
        }
    }
}
