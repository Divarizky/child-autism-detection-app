package com.application.divarizky.autismdetection.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.application.divarizky.autismdetection.view.theme.LightGrey
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.NunitoSansFamily
import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel

private val ICON_SIZE = 28.dp

@Composable
fun BottomNavigationBar(
    navController: NavController,
    bottomNavbarViewModel: BottomNavbarViewModel
) {
    // Untuk mengambil rute saat ini dari back stack entry
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        bottomNavbarViewModel.navigationItems.forEach { item ->
            val iconTintColor = if (currentRoute == item.route) MediumBlue else LightGrey
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = iconTintColor,
                        modifier = Modifier.size(ICON_SIZE)
                    )
                },
                label = {
                    Text(
                        item.label,
                        fontFamily = NunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = iconTintColor,
                        fontSize = 10.sp
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        bottomNavbarViewModel.onNavigationItemSelected(item.route)
                        navController.navigate(item.route) {
                            popUpTo("home_screen") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
