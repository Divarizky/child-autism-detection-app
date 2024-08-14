package com.application.divarizky.autismdetection.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//fun <viewModel: ViewModel> viewModelFactory(initializer: () -> viewModel): ViewModelProvider.Factory {
//    return object : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return initializer() as T
//        }
//    }
//}

fun <VM : ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(initializer().javaClass)) {
                return initializer() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
