package com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks

sealed class TasksPageEffect {
    data class ShowToast(val message: String) : TasksPageEffect()
}
