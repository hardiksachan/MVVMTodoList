package com.hardiksachan.mvvmtodolist.ui.page_view_tasks

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.hardiksachan.mvvmtodolist.R
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components.TaskItem
import com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components.TasksHeader
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun TasksPage(
    tasks: List<Task>,
    onAddButtonClicked: () -> Unit,
    searchDisplay: String,
    onTaskCheckChanged: (Task, Boolean) -> Unit,
    onSearchDisplayChanged: (String) -> Unit,
    sortMenuVisible: Boolean,
    onSortMenuDismissRequest: () -> Unit,
    onSortMenuClicked: () -> Unit,
    onSortByNameClicked: () -> Unit,
    onSortByDateClicked: () -> Unit
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddButtonClicked() }) {
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
                onSearchDisplayChanged = onSearchDisplayChanged,
                onSortMenuClicked = onSortMenuClicked,
                onSortMenuDismissRequest = onSortMenuDismissRequest,
                onSortByNameClicked = onSortByNameClicked,
                onSortByDateClicked = onSortByDateClicked,
                sortMenuVisible = sortMenuVisible
            )
        }
    ) {
        LazyColumn {
            items(tasks) { task: Task ->
                TaskItem(task = task,
                    onCheckChanged = {
                        onTaskCheckChanged(task, it)
                    }
                )
            }
        }
    }
}

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
            onAddButtonClicked = {},
            searchDisplay = "",
            onSearchDisplayChanged = { },
            onTaskCheckChanged = { _, _ -> },
            onSortMenuDismissRequest = {},
            onSortByNameClicked = {},
            onSortByDateClicked = {},
            onSortMenuClicked = {},
            sortMenuVisible = true
        )
    }
}