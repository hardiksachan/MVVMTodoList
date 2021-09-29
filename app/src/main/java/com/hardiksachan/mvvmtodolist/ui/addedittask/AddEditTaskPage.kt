package com.hardiksachan.mvvmtodolist.ui.addedittask

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardiksachan.mvvmtodolist.R
import com.hardiksachan.mvvmtodolist.ui.addedittask.components.CreatedLabel
import com.hardiksachan.mvvmtodolist.ui.addedittask.components.ImportantTaskCheckbox
import com.hardiksachan.mvvmtodolist.ui.addedittask.components.TaskNameTextField
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun AddEditTask(
    appBarTitle: String = "Add Task",
    taskName: String = "",
    isImportant: Boolean = false,
    createdOn: String = ""
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_mark),
                    contentDescription = "Save Icon",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopAppBar(
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text(
                    text = appBarTitle,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            TaskNameTextField(
                taskName = taskName,
                onTaskNameChanged = { /* TODO */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ImportantTaskCheckbox(
                checked = isImportant,
                onCheckedChange = { /* TODO */ }
            )

            Spacer(modifier = Modifier.height(8.dp))
            CreatedLabel(
                date = createdOn,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPagePreview() {
    AppTheme {
        AddEditTask()
    }
}

@Preview(showBackground = true)
@Composable
fun EditPagePreview() {
    AppTheme {
        AddEditTask(
            appBarTitle = "Edit Task",
            taskName = "Do something",
            createdOn = "Nov 11, 2020 9:44:38 AM"
        )
    }
}