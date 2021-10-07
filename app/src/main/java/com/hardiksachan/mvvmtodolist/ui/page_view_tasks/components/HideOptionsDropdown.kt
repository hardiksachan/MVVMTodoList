package com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HideOptionsDropdown(
    expanded: Boolean,
    showCompleted: Boolean,
    onDismissRequest: () -> Unit,
    showCompletedToggled: () -> Unit,
    deleteCompletedClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() }
    ) {
        DropdownMenuItem(onClick = { showCompletedToggled() }) {
            Text("Show Completed")
            Spacer(modifier = Modifier.width(16.dp))
            Checkbox(
                checked = showCompleted,
                onCheckedChange = { showCompletedToggled() }
            )
        }
        Divider()
        DropdownMenuItem(onClick = { deleteCompletedClicked() }) {
            Text("Delete All Completed")
        }
    }
}
