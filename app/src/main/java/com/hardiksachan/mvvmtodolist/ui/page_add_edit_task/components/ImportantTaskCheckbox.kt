package com.hardiksachan.mvvmtodolist.ui.page_add_edit_task.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun ImportantTaskCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {
                onCheckedChange(!checked)
            }
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckedChange(it)
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Important Task")
    }
}

@Preview(showBackground = true)
@Composable
fun UncheckedImportantTaskCheckboxPreview() {
    AppTheme {
        ImportantTaskCheckbox(
            checked = false,
            onCheckedChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckedImportantTaskCheckboxPreview() {
    AppTheme {
        ImportantTaskCheckbox(
            checked = true,
            onCheckedChange = {}
        )
    }
}