@file:OptIn(ExperimentalMaterial3Api::class)

package com.quiraadev.notez.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.quiraadev.notez.presentation.events.NotesEvent
import com.quiraadev.notez.presentation.states.NoteState
import com.quiraadev.notez.ui.navigator.Screen

@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavHostController,
    onEvent: (NotesEvent) -> Unit
) {

    return Scaffold(
        topBar = {
            Surface(modifier = Modifier.fillMaxWidth(), shadowElevation = 4.dp) {
                LargeTopAppBar(
                    title = {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            value = state.title.value,
                            onValueChange = {
                                state.title.value = it
                            },
                            textStyle = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            ),
                            placeholder = {
                                Text(text = "Title")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = Color.Black,
                                unfocusedPlaceholderColor = Color.Gray
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            Screen.pop(navController)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBackIosNew,
                                contentDescription = null,
                                tint = Color.Blue
                            )
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color.Blue,
                shape = CircleShape,
                onClick = {
                    onEvent(
                        NotesEvent.SaveNote(
                            title = state.title.value,
                            content = state.content.value
                        )
                    )
                    Screen.pop(navController)
                }) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Note",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.content.value,
                onValueChange = {
                    state.content.value = it
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                placeholder = {
                    Text(text = "Content")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.Black,
                    unfocusedPlaceholderColor = Color.Gray
                )
            )

        }
    }
}