package com.hardiksachan.mvvmtodolist.ui.feature_view_tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardiksachan.mvvmtodolist.R
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = task.completed, onCheckedChange = {})
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = task.name,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textDecoration = if (task.completed) TextDecoration.LineThrough else null
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (task.important) {
            Icon(
                painter = painterResource(id = R.drawable.ic_priority),
                contentDescription = "Priority Icon",
                tint = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    AppTheme {
        Surface {
            TaskItem(task = Task("A very simple and boring task"))
        }
    }
}


@Preview
@Composable
fun ImportantTaskItemPreview() {
    AppTheme {
        Surface {
            TaskItem(
                task = Task(
                    "An important task which has a really long name",
                    important = true
                )
            )
        }
    }
}


@Preview
@Composable
fun CompletedTaskItemPreview() {
    AppTheme {
        Surface {
            TaskItem(
                task = Task(
                    "A completed task with a considerable number of letters",
                    completed = true
                )
            )
        }
    }
}