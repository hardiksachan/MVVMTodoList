package com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardiksachan.mvvmtodolist.R
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksPageEvent
import com.hardiksachan.mvvmtodolist.ui.components.ExpandableSearchView
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme


@Composable
fun TasksHeader(
    searchDisplay: String,
    sortMenuVisible: Boolean,
    hideOptionsMenuVisible: Boolean,
    showCompleted: Boolean,
    onEvent: (TasksPageEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        elevation = 8.dp,
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.primary,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExpandableSearchView(
                searchDisplay = searchDisplay,
                onSearchDisplayChanged = { onEvent(TasksPageEvent.SearchQueryChanged(it)) },
                onSearchDisplayClosed = { },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            IconButton(onClick = { onEvent(TasksPageEvent.SortMenuToggled) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = "sort icon"
                )
                SortDropdown(
                    expanded = sortMenuVisible,
                    onEvent = onEvent
                )
            }
            IconButton(onClick = { onEvent(TasksPageEvent.HideOptionsMenuToggled) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = "more icon"
                )
                HideOptionsDropdown(
                    expanded = hideOptionsMenuVisible,
                    showCompleted = showCompleted,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TasksHeaderPreview() {

    AppTheme {
        TasksHeader(
            searchDisplay = "",
            sortMenuVisible = false,
            hideOptionsMenuVisible = false,
            showCompleted = true,
            onEvent = { },
        )
    }
}