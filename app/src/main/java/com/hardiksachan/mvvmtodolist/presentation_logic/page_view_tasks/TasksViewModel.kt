package com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardiksachan.mvvmtodolist.common.IDispatcherProvider
import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.domain.repository.ITaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TasksViewModel"

@ExperimentalCoroutinesApi
@HiltViewModel
class TasksViewModel
@Inject
constructor(
    private val taskRepository: ITaskRepository,
    private val dispatcherProvider: IDispatcherProvider
) : ViewModel() {

    fun onEvent(event: TasksPageEvent) {
        when (event) {
            is TasksPageEvent.SearchQueryChanged -> handleSearchQueryChanged(query = event.newQuery)
            is TasksPageEvent.TaskCheckedChanged -> handleTaskChecked(
                task = event.task,
                completed = event.checked
            )
        }
    }

    private fun handleTaskChecked(task: Task, completed: Boolean) {
        viewModelScope.launch {
            taskRepository.saveTask(task.copy(completed = completed))
        }
    }

    private fun handleSearchQueryChanged(query: String) {
        viewModelScope.launch {
            _searchDisplay.emit(query)
        }
    }


    // FOR UI STATE (PRIVATE)
    private val _tasks = MutableStateFlow(emptyList<Task>())
    private val _searchDisplay = MutableStateFlow("")
    private val _effectStream = MutableSharedFlow<TasksPageEffect>()

    // FOR UI STATE (PUBLIC)
    val tasks: StateFlow<List<Task>> = _tasks
    val searchDisplay: StateFlow<String> = _searchDisplay
    val effectStream: SharedFlow<TasksPageEffect> = _effectStream

    init {
        viewModelScope.launch {
            _searchDisplay.flatMapLatest { searchQuery ->
                taskRepository.getFilteredTasks(nameQuery = searchQuery)
            }.map { result ->
                Log.d(TAG, "taskResult: $result")
                when (result) {
                    is ResultWrapper.Failure -> {
                        // todo: handle error case
                        emptyList()
                    }
                    is ResultWrapper.Success -> result.result
                }
            }.collect { tasks ->
                _tasks.emit(tasks)
            }
        }
    }
}