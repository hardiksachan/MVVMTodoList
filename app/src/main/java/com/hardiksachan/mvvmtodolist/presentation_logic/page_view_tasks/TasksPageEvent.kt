package com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks

import com.hardiksachan.mvvmtodolist.domain.entity.Task

sealed class TasksPageEvent {
    data class SearchQueryChanged(val newQuery: String): TasksPageEvent()
    data class TaskCheckedChanged(val task: Task, val checked: Boolean): TasksPageEvent()
}
