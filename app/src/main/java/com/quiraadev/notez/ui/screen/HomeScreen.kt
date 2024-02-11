package com.quiraadev.notez.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Article
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.quiraadev.notez.presentation.common.NoteState
import com.quiraadev.notez.presentation.common.TodoState
import com.quiraadev.notez.presentation.viewmodel.NoteViewModel
import com.quiraadev.notez.presentation.viewmodel.TodoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    noteState : NoteState,
    todoState: TodoState,
    navController: NavHostController,
    noteViewModel: NoteViewModel,
    todoViewModel: TodoViewModel
) {
    val pagerState = rememberPagerState(pageCount = { 2 }, initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    val tabItems = listOf(
        TabItem(
            "Note",
            Icons.AutoMirrored.Rounded.Article
        ) {
            NoteScreen(
                state = noteState,
                navController = navController,
                onEvent = noteViewModel::onNoteEvent,
                noteViewModel = noteViewModel
            )
        },
        TabItem(
            "Todo",
            Icons.Rounded.EditNote
        ) {
            TodoScreen(
                state = todoState,
                todoViewModel = todoViewModel,
                onEvent = todoViewModel::onTodoEvent
            )
        }
    )

    return Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "MemoPad",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text(
                text = "Simply create Notes and Tasks in no time",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                contentColor = TabRowDefaults.contentColor,
                containerColor = TabRowDefaults.containerColor,
                selectedTabIndex = pagerState.currentPage,
                divider = {
                    Divider()
                },
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    )
                },
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = pagerState.currentPage == index,

                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 14.dp)
                        ) {
                            Icon(imageVector = tabItem.icon, contentDescription = null)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = tabItem.label, style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
            HorizontalPager(state = pagerState) { page ->
                tabItems[page].screen()
            }
        }
    }
}


data class TabItem(
    val label: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit
)