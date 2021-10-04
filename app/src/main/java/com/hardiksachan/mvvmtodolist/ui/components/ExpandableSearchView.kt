package com.hardiksachan.mvvmtodolist.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardiksachan.mvvmtodolist.R
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun ExpandableSearchView(
    searchDisplay: String,
    onSearchDisplayChanged: (String) -> Unit,
    onSearchDisplayClosed: () -> Unit,
    modifier: Modifier = Modifier,
    expandedInitially: Boolean = false,
    tint: Color = MaterialTheme.colors.onPrimary
) {
    val (expanded, onExpandedChanged) = remember {
        mutableStateOf(expandedInitially)
    }


    Crossfade(targetState = expanded) { isSearchFieldVisible ->
        when (isSearchFieldVisible) {
            true -> CollapsedSearchView(
                searchDisplay = searchDisplay,
                onSearchDisplayChanged = onSearchDisplayChanged,
                onSearchDisplayClosed = onSearchDisplayClosed,
                onExpandedChanged = onExpandedChanged,
                modifier = modifier,
                tint = tint
            )

            false -> ExpandedSearchView(
                onExpandedChanged = onExpandedChanged,
                modifier = modifier,
                tint = tint
            )
        }
    }
}

@Composable
fun SearchIcon(iconTint: Color) {
    Icon(
        painter = painterResource(id = R.drawable.ic_search),
        contentDescription = "search icon",
        tint = iconTint
    )
}

@Composable
fun ExpandedSearchView(
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onPrimary,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Tasks",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(start = 16.dp)
        )
        IconButton(onClick = { onExpandedChanged(true) }) {
            SearchIcon(iconTint = tint)
        }
    }
}

@Composable
fun CollapsedSearchView(
    searchDisplay: String,
    onSearchDisplayChanged: (String) -> Unit,
    onSearchDisplayClosed: () -> Unit,
    onExpandedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onPrimary,
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            onExpandedChanged(false)
            onSearchDisplayClosed()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "back icon",
                tint = tint
            )
        }
        TextField(
            value = searchDisplay,
            onValueChange = { onSearchDisplayChanged(it) },
            trailingIcon = {
                SearchIcon(iconTint = tint)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Search", color = tint)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }
}

@Preview
@Composable
fun CollapsedSearchViewPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colors.primary
        ) {
            ExpandableSearchView(
                searchDisplay = "",
                onSearchDisplayChanged = {},
                onSearchDisplayClosed = {}
            )
        }
    }
}

@Preview
@Composable
fun ExpandedSearchViewPreview() {
    AppTheme {
        Surface(
            color = MaterialTheme.colors.primary
        ) {
            ExpandableSearchView(
                searchDisplay = "",
                onSearchDisplayChanged = {},
                expandedInitially = true,
                onSearchDisplayClosed = {}
            )
        }
    }
}