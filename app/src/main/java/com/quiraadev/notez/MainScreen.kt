package com.quiraadev.notez

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quiraadev.notez.presentation.viewmodel.HomeViewModel
import com.quiraadev.notez.ui.navigator.Screen
import com.quiraadev.notez.ui.screen.AddNoteScreen
import com.quiraadev.notez.ui.screen.HomeScreen

@Composable
fun MainScreen(
    homeViewModel: HomeViewModel
) {
    val navController = rememberNavController()
    val state by homeViewModel.state.collectAsState()

    return NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                noteState = state,
                navController = navController,
                onEvent = homeViewModel::onEvent
            )
        }
        composable(Screen.AddNoteScreen.route) {
            AddNoteScreen(
                state = state,
                navController = navController,
                onEvent = homeViewModel::onEvent
            )
        }
    }
}