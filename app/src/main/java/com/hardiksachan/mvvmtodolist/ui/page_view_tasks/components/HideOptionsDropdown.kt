package com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksPageEvent


@Composable
fun HideOptionsDropdown(
    expanded: Boolean,
    showCompleted: Boolean,
    onEvent: (TasksPageEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onEvent(TasksPageEvent.HideOptionsMenuDismissed) }
    ) {
        DropdownMenuItem(onClick = { onEvent(TasksPageEvent.ShowCompletedToggled) }) {
            Text("Show Completed")
            Spacer(modifier = Modifier.width(16.dp))
            Checkbox(
                checked = showCompleted,
                onCheckedChange = { onEvent(TasksPageEvent.ShowCompletedToggled) }
            )
        }
        Divider()
        DropdownMenuItem(onClick = { onEvent(TasksPageEvent.DeleteCompletedTasksRequested) }) {
            Text("Delete All Completed")
        }
    }
}
