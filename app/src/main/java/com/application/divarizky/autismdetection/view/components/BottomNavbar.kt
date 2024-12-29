package com.application.divarizky.autismdetection.view.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.application.divarizky.autismdetection.view.theme.LightGrey
import com.application.divarizky.autismdetection.view.theme.MediumBlue
import com.application.divarizky.autismdetection.view.theme.NunitoSansFamily
import com.application.divarizky.autismdetection.viewmodel.BottomNavbarViewModel

private val BOTTOM_NAV_HEIGHT = 60.dp
private val ICON_SIZE = 28.dp

@Composable
fun BottomNavbar(
    navController: NavHostController,
    bottomNavbarViewModel: BottomNavbarViewModel = viewModel()
) {
    val currentRoute by rememberUpdatedState(bottomNavbarViewModel.currentRoute.value)

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(BOTTOM_NAV_HEIGHT)
    ) {
        bottomNavbarViewModel.navigationItems.forEach { item ->
            val iconTintColor = if (currentRoute == item.route) MediumBlue else LightGrey

            NavigationBarItem(
                icon = {
                    Icon(
                        ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.label,
                        tint = iconTintColor,
                        modifier = Modifier.size(ICON_SIZE)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontFamily = NunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        color = iconTintColor,
                        fontSize = 10.sp
                    )
                },
                selected = currentRoute == item.route,
                alwaysShowLabel = true,
                onClick = {
                    bottomNavbarViewModel.onNavigationItemSelected(item.route)
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MediumBlue,
                    unselectedIconColor = LightGrey,
                    selectedTextColor = MediumBlue,
                    unselectedTextColor = LightGrey,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

