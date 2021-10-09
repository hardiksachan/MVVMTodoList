package com.hardiksachan.mvvmtodolist.ui.page_view_tasks

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.hardiksachan.mvvmtodolist.R
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksPageEvent
import com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components.TaskListItem
import com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components.TasksHeader
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@ExperimentalMaterialApi
@Composable
fun TasksPage(
    tasks: List<Task>,
    searchDisplay: String,
    sortMenuVisible: Boolean,
    hideOptionsMenuVisible: Boolean,
    showCompleted: Boolean,
    snackbarHostState: SnackbarHostState,
    onEvent: (TasksPageEvent) -> Unit
) {

    val scaffoldState = rememberScaffoldState(
        snackbarHostState = snackbarHostState
    )

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Icon",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TasksHeader(
                searchDisplay = searchDisplay,
                sortMenuVisible = sortMenuVisible,
                hideOptionsMenuVisible = hideOptionsMenuVisible,
                showCompleted = showCompleted,
                onEvent = onEvent
            )
        }
    ) {
        LazyColumn {
            items(
                items = tasks,
                key = { it.id }
            ) { task: Task ->
                TaskListItem(
                    task = task,
                    onEvent = onEvent
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun TasksPagePreview() {

    val tasks: List<Task> = (1..5).map { idx: Int ->
        Task(
            "Task $idx",
            completed = (idx % 3 == 0),
            important = (idx < 2)
        )
    }

    AppTheme {
        TasksPage(
            tasks = tasks,
            searchDisplay = "",
            sortMenuVisible = false,
            hideOptionsMenuVisible = false,
            showCompleted = true,
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}