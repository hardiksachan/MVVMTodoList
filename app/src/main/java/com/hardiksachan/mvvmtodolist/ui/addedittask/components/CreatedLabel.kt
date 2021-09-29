package com.hardiksachan.mvvmtodolist.ui.addedittask.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hardiksachan.mvvmtodolist.ui.theme.AppTheme

@Composable
fun CreatedLabel(
    date: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Created: $date",
        modifier = modifier,
        style = MaterialTheme.typography.body2.copy(color = Color.Gray)
    )
}


@Preview(showBackground = true)
@Composable
fun CreatedLabelPreview() {
    AppTheme {
         CreatedLabel("Nov 11, 2020 9:44:38 AM")
    }
}