package com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks

import androidx.lifecycle.ViewModel
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class TasksViewModel : ViewModel() {

    fun onEvent(event: TasksPageEvent) {
        TODO()
    }


    // FOR UI STATE (PRIVATE)
    private val _tasks = MutableStateFlow(emptyList<Task>())
    private val _searchDisplay = MutableStateFlow("")
    private val _effectStream = MutableSharedFlow<TasksPageEffect>()

    // FOR UI STATE (PUBLIC)
    val tasks: StateFlow<List<Task>> = _tasks
    val searchDisplay: StateFlow<String> = _searchDisplay
    val effectStream: SharedFlow<TasksPageEffect> = _effectStream
}