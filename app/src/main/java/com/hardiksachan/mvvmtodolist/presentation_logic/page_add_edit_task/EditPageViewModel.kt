package com.hardiksachan.mvvmtodolist.presentation_logic.page_add_edit_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardiksachan.mvvmtodolist.common.IDispatcherProvider
import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.domain.repository.ITaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TITLE_NEW_PAGE = "New Task"
const val TITLE_EDIT_PAGE = "Edit Task"

@HiltViewModel
class EditPageViewModel
@Inject
constructor(
    private val taskRepository: ITaskRepository,
    private val dispatcherProvider: IDispatcherProvider
) : ViewModel() {
    var editingTask: Task? = null

    fun onEvent(event: EditPageEvent) = when (event) {
        is EditPageEvent.InitWithTask -> handleInitWithTask(taskId = event.taskId)
        EditPageEvent.IsImportantToggled -> TODO()
        is EditPageEvent.NameChanged -> TODO()
    }

    private fun handleInitWithTask(taskId: String) {
        viewModelScope.launch {
            when (val taskWrapper = taskRepository.getTaskWithId(taskId)) {
                is ResultWrapper.Failure -> TODO()
                is ResultWrapper.Success -> {
                    editingTask = taskWrapper.result
                    editingTask?.also {
                        _nameDisplay.emit(it.name)
                        _isImportant.emit(it.important)
                        _pageTitle.emit(TITLE_EDIT_PAGE)
                    }
                }
            }
        }
    }

    // UI State (private)
    private val _nameDisplay = MutableStateFlow("")
    private val _isImportant = MutableStateFlow(false)
    private val _pageTitle = MutableStateFlow(TITLE_NEW_PAGE)

    // UI State (public)
    val nameDisplay: StateFlow<String> = _nameDisplay
    val isImportant: StateFlow<Boolean> = _isImportant
    val pageTitle: StateFlow<String> = _pageTitle
}