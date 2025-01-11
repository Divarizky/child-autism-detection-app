package com.application.divarizky.autismdetection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.divarizky.autismdetection.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    // LiveData untuk menyimpan username
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    init {
        loadUsername() // Memuat username saat ViewModel dibuat
    }

    // Fungsi untuk memuat username dari repository
    private fun loadUsername() {
        viewModelScope.launch {
            val user = userRepository.getLoggedInUser()
            _username.value = user?.username ?: "Guest"
        }
    }
}
