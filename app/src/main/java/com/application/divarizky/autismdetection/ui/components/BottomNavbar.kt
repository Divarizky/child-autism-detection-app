package com.application.divarizky.autismdetection.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.application.divarizky.autismdetection.ui.theme.LightGrey
import com.application.divarizky.autismdetection.ui.theme.MediumBlue
import com.application.divarizky.autismdetection.ui.theme.NunitoSansFamily
import com.application.divarizky.autismdetection.ui.viewmodel.HomeViewModel

@Composable
fun BottomNavbar(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val currentRoute = homeViewModel.currentRoute.value

    NavigationBar(
        containerColor = Color.White
    ) {
        homeViewModel.navigationItems.forEach { item ->
            val iconTintColor = if (currentRoute == item.route) MediumBlue else LightGrey

            NavigationBarItem(
                icon = {
                    Icon(
                        ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.label,
                        tint = iconTintColor
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontFamily = NunitoSansFamily,
                        color = iconTintColor
                    )
                },
                selected = currentRoute == item.route,
                alwaysShowLabel = true,
                onClick = {
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

@Preview(showBackground = true)
@Composable
fun BottomNavbarPreview() {
    BottomNavbar(navController = NavHostController(LocalContext.current))
}
