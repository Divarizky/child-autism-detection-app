package com.application.divarizky.autismdetection.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.application.divarizky.autismdetection.R
import com.application.divarizky.autismdetection.data.model.NavigationItems

class BottomNavbarViewModel : ViewModel() {

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
    var currentRoute = mutableStateOf("home_screen")
        private set

    val navigationItems: List<NavigationItems> = _navigationItems

    fun onNavigationItemSelected(route: String) {
        currentRoute.value = route
    }
}
