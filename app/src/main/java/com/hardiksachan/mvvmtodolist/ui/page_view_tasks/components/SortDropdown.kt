package com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun SortDropdown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onSortByNameClicked: () -> Unit,
    onSortByDateClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() }
    ) {
        DropdownMenuItem(onClick = { onSortByNameClicked() }) {
            Text("Sort by Name")
        }
        DropdownMenuItem(onClick = { onSortByDateClicked() }) {
            Text("Sort by Date")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortDropdownPreview() {
    AppTheme {
        SortDropdown(
            expanded = true,
            onSortByDateClicked = {},
            onDismissRequest = {},
            onSortByNameClicked = {}
        )
    }
}