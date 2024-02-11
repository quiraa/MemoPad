@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.quiraadev.notez

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quiraadev.notez.presentation.viewmodel.NoteViewModel
import com.quiraadev.notez.presentation.viewmodel.TodoViewModel
import com.quiraadev.notez.ui.navigator.Screen
import com.quiraadev.notez.ui.screen.AddNoteScreen
import com.quiraadev.notez.ui.screen.HomeScreen

@Composable
fun MainScreen(
    noteViewModel: NoteViewModel,
    todoViewModel: TodoViewModel
) {
    val navController = rememberNavController()

    val noteState by noteViewModel.noteState.collectAsState()
    val todoState by todoViewModel.todoState.collectAsState()

    return NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = {
            fadeIn(animationSpec = tween(500)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(500)
            )
        },
        exitTransition = {
            fadeOut(animationSpec = tween(500)) + slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, tween(500)
            )
        },
        builder = {
            composable(Screen.Home.route) {
                HomeScreen(
                    noteState = noteState,
                    todoState = todoState,
                    navController = navController,
                    noteViewModel = noteViewModel,
                    todoViewModel = todoViewModel
                )
            }
            composable(Screen.AddNoteScreen.route) {
                AddNoteScreen(
                    state = noteState,
                    navController = navController,
                    onEvent = noteViewModel::onNoteEvent,
                    noteViewModel = noteViewModel,
                )
            }
        },
    )
}
