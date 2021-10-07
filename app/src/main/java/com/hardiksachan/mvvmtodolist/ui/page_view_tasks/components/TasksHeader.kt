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
import com.hardiksachan.mvvmtodolist.ui.components.ExpandableSearchView
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme


@Composable
fun TasksHeader(
    searchDisplay: String,
    onSearchDisplayChanged: (String) -> Unit,
    onSortMenuClicked: () -> Unit,
    onSearchDisplayClosed: () -> Unit,
    sortMenuVisible: Boolean,
    onSortMenuDismissRequest: () -> Unit,
    onSortByNameClicked: () -> Unit,
    onSortByDateClicked: () -> Unit,
    hideOptionsMenuVisible: Boolean,
    onHideOptionsMenuDismissRequest: () -> Unit,
    showCompleted: Boolean,
    onShowCompletedToggled: () -> Unit,
    onDeleteCompletedClicked: () -> Unit,
    onHideOptionsMenuClicked: () -> Unit,
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
                onSearchDisplayChanged = onSearchDisplayChanged,
                onSearchDisplayClosed = onSearchDisplayClosed,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            IconButton(onClick = { onSortMenuClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = "sort icon"
                )
                SortDropdown(
                    expanded = sortMenuVisible,
                    onDismissRequest = onSortMenuDismissRequest,
                    onSortByNameClicked = onSortByNameClicked,
                    onSortByDateClicked = onSortByDateClicked,
                )
            }
            IconButton(onClick = onHideOptionsMenuClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = "more icon"
                )
                HideOptionsDropdown(
                    expanded = hideOptionsMenuVisible,
                    onDismissRequest = onHideOptionsMenuDismissRequest,
                    showCompleted = showCompleted,
                    showCompletedToggled = onShowCompletedToggled,
                    deleteCompletedClicked = onDeleteCompletedClicked,
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
            onSearchDisplayChanged = { },
            onSortMenuClicked = { },
            onSearchDisplayClosed = { },
            sortMenuVisible = false,
            onSortMenuDismissRequest = { },
            onSortByNameClicked = { },
            onSortByDateClicked = { },
            hideOptionsMenuVisible = false,
            onHideOptionsMenuDismissRequest = { },
            showCompleted = true,
            onShowCompletedToggled = { },
            onDeleteCompletedClicked = { },
            onHideOptionsMenuClicked = { })
    }
}