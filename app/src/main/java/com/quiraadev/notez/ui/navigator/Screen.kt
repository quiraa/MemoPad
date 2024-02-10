package com.quiraadev.notez.ui.navigator

import androidx.navigation.NavHostController

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object AddNoteScreen : Screen("addnote")

    companion object {
        fun push(navController: NavHostController, route: String) {
            navController.navigate(route)
        }

        fun pushAndReplace(navController: NavHostController, route: String) {
            navController.navigate(route) {
                popUpTo(navController.graph.id) {
                    saveState = true
                    inclusive = true
                }
                restoreState = true
                launchSingleTop = true
            }
        }

        fun pop(navController: NavHostController) {
            navController.popBackStack()
        }
    }
}