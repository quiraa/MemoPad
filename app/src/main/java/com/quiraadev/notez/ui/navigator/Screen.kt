package com.quiraadev.notez.ui.navigator

import androidx.navigation.NavHostController

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Note: Screen("note")
    data object Todo: Screen("todo")
    data object AddNoteScreen : Screen("addnote")

    companion object {
        fun push(navController: NavHostController, route: String) {
            navController.navigate(route)
        }

        fun pop(navController: NavHostController) {
            navController.popBackStack()
        }
    }
}