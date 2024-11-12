package com.mufr.klinikku.presentation.ui.bottombar

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mufr.klinikku.presentation.navigation.MainBottomBarNav
import com.mufr.klinikku.presentation.navigation.Screen

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            MainBottomBarNav(
                "History",
                Icons.AutoMirrored.Rounded.ListAlt,
                Screen.History
            ),
            MainBottomBarNav(
                "Hospital",
                Icons.Rounded.Home,
                Screen.Hospital
            ),
            MainBottomBarNav(
                "Profile",
                Icons.Rounded.Person,
                Screen.Profile
            )
        )

        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    Log.d("Current Route : " , currentRoute.toString())
                    Log.d("Targer Route : " , item.screen.route)
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                })
        }
    }
}