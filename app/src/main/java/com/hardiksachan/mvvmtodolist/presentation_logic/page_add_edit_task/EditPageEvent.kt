package com.hardiksachan.mvvmtodolist.presentation_logic.page_add_edit_task

sealed class EditPageEvent {
    data class InitWithTask(val taskId: String): EditPageEvent()
    data class NameChanged(val newName: String): EditPageEvent()
    object IsImportantToggled: EditPageEvent()
    object SaveAndExit: EditPageEvent()
    object IgnoreAndExit: EditPageEvent()
}