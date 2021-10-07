package com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksPageEvent

@Composable
fun SortDropdown(
    expanded: Boolean,
    onEvent: (TasksPageEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onEvent(TasksPageEvent.SortMenuDismissed)  }
    ) {
        DropdownMenuItem(onClick = {  onEvent(TasksPageEvent.SortByRequested(SortOrder.BY_NAME)) }) {
            Text("Sort by Name")
        }
        DropdownMenuItem(onClick = { onEvent(TasksPageEvent.SortByRequested(SortOrder.BY_DATE)) }) {
            Text("Sort by Date")
        }
    }
}
