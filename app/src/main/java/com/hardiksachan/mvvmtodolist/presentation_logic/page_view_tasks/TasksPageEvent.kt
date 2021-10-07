package com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks

import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder
import com.hardiksachan.mvvmtodolist.domain.entity.Task

sealed class TasksPageEvent {
    data class SearchQueryChanged(val newQuery: String): TasksPageEvent()
    data class TaskCheckedChanged(val task: Task, val checked: Boolean): TasksPageEvent()
    object SortMenuToggled: TasksPageEvent()
    object SortMenuDismissed: TasksPageEvent()
    object HideOptionsMenuToggled: TasksPageEvent()
    object HideOptionsMenuDismissed: TasksPageEvent()
    data class SortByRequested(val sortOrder: SortOrder): TasksPageEvent()
    object ShowCompletedToggled: TasksPageEvent()
    object DeleteCompletedTasksRequested: TasksPageEvent()
}
