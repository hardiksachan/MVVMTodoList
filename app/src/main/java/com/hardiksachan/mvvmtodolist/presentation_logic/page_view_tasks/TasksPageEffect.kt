package com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks

import com.hardiksachan.mvvmtodolist.domain.entity.Task

sealed class TasksPageEffect {
    data class ShowUndoDeleteTaskMessage(val task: Task) : TasksPageEffect()
    object NavigateToAddTask : TasksPageEffect()
    data class NavigateToEditTask(val task: Task) : TasksPageEffect()
}
