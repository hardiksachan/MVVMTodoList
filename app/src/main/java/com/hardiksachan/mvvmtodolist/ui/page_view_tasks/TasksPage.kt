package com.hardiksachan.mvvmtodolist.ui.page_view_tasks

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardiksachan.mvvmtodolist.R
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components.TaskItem
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun TasksPage(
    tasks: List<Task>,
    onAddButtonClicked: () -> Unit
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
            TopAppBar(
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text(text = "Tasks", style = MaterialTheme.typography.h6)
            }
        }
    ) {
        LazyColumn {
            items(tasks) { task: Task ->
                TaskItem(task = task)
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
            onAddButtonClicked = {}
        )
    }
}