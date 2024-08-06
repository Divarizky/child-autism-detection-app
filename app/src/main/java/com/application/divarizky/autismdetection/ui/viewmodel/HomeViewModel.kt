package com.application.divarizky.autismdetection.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.data.model.NavigationItems

class HomeViewModel : ViewModel() {

    // Bottom navigation items
    private val _navigationItems = listOf(
        NavigationItems(
            label = "Home",
            icon = R.drawable.ic_home,
            route = "home_screen"
        ),
        NavigationItems(
            label = "Scan",
            icon = R.drawable.ic_scan,
            route = "autism_detection_screen"
        ),
        NavigationItems(
            label = "Settings",
            icon = R.drawable.ic_setting,
            route = "settings_screen"
        )
    )

    // Define BottomNavigation logic
    private val _currentRoute = MutableLiveData("home_screen")
    val currentRoute: LiveData<String> = _currentRoute

    val navigationItems: List<NavigationItems> = _navigationItems

    fun onNavigationItemSelected(route: String) {
        _currentRoute.value = route
    }
}