package com.hardiksachan.mvvmtodolist.ui.page_view_tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksPageEvent
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun TaskListItem(
    task: Task,
    onEvent: (TasksPageEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconWidth = (-48).dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { iconWidth.toPx() }
    val anchors = mapOf(
        0f to 0,
        sizePx to 1
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .then(modifier)
            .background(Color.Red)
    ) {
        Icon(
            painter = rememberVectorPainter(Icons.Default.Delete),
            contentDescription = "Delete Icon",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
                .clickable {
                    onEvent(TasksPageEvent.DeleteTaskRequested(task))
                },
            tint = Color.White
        )
        TaskTile(
            task = task,
            onEvent = onEvent,
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }
                .background(MaterialTheme.colors.background)
        )
    }
}