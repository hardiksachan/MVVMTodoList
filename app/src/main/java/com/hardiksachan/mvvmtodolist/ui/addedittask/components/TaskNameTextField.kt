package com.hardiksachan.mvvmtodolist.ui.addedittask.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun TaskNameTextField(
    taskName: String,
    onTaskNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = taskName,
        onValueChange = {
            onTaskNameChanged(it)
        },
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Task Name",
                textAlign = TextAlign.Start
            )
        },
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyTaskNameTextFieldPreview() {
    AppTheme {
        TaskNameTextField(
            taskName = "",
            onTaskNameChanged = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FullTaskNameTextFieldPreview() {
    AppTheme {
        TaskNameTextField(
            taskName = "Hello World",
            onTaskNameChanged = {}
        )
    }
}