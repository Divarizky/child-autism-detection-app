package com.application.divarizky.autismdetection.viewmodel

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.divarizky.autismdetection.data.model.User
import com.application.divarizky.autismdetection.data.repository.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user: MutableLiveData<User?> = MutableLiveData()
    val user: LiveData<User?> = _user

    var scrollState: ScrollState by mutableStateOf(ScrollState(0))

    fun updateScrollState(newScrollState: ScrollState) {
        scrollState = newScrollState
    }

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            _user.value = userRepository.getUserById(userId)
        }
    }

    fun logout(onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            userRepository.logout()
            onLogoutComplete()
        }
    }
}

