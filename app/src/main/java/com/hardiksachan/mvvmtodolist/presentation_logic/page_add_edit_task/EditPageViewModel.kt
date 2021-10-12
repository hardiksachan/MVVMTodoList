package com.hardiksachan.mvvmtodolist.presentation_logic.page_add_edit_task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardiksachan.mvvmtodolist.common.IDispatcherProvider
import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.domain.repository.ITaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TITLE_NEW_PAGE = "New Task"
const val TITLE_EDIT_PAGE = "Edit Task"

private const val TAG = "EditPageViewModel"

@HiltViewModel
class EditPageViewModel
@Inject
constructor(
    private val taskRepository: ITaskRepository,
    private val dispatcherProvider: IDispatcherProvider
) : ViewModel() {
    private var editingTask: Task? = null

    fun onEvent(event: EditPageEvent) = when (event) {
        is EditPageEvent.InitWithTask -> handleInitWithTask(taskId = event.taskId)
        EditPageEvent.IsImportantToggled -> handleIsImportantToggled()
        is EditPageEvent.NameChanged -> handleNameChanged(newName = event.newName)
        EditPageEvent.IgnoreAndExit -> handleIgnoreAndExit()
        EditPageEvent.SaveAndExit -> handleSaveAndExit()
    }

    private fun handleNameChanged(newName: String) {
        viewModelScope.launch {
            _nameDisplay.emit(newName)
        }
    }

    private fun handleIsImportantToggled() {
        viewModelScope.launch {
            _isImportant.emit(_isImportant.value.not())
        }
    }

    private fun handleSaveAndExit() {
        viewModelScope.launch {
            taskRepository.saveTask(
                editingTask?.copy(
                    name = _nameDisplay.value,
                    important = _isImportant.value
                ) ?: Task(
                    name = _nameDisplay.value,
                    important = _isImportant.value
                )
            )
            _effectStream.emit(EditPageEffect.NavigateToListPage)
        }
    }

    private fun handleIgnoreAndExit() {
        viewModelScope.launch {
            _effectStream.emit(EditPageEffect.NavigateToListPage)
        }
    }

    private fun handleInitWithTask(taskId: String) {
        viewModelScope.launch {
            when (val taskWrapper = taskRepository.getTaskWithId(taskId)) {
                is ResultWrapper.Failure -> {
                    // TODO: handle Error Case
                    Log.e(TAG, "handleInitWithTask: failed to load task with id: $taskId")
                    Log.e(TAG, "handleInitWithTask: ${taskWrapper.error}", )
                }
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
    private val _effectStream = MutableSharedFlow<EditPageEffect>()

    // UI State (public)
    val nameDisplay: StateFlow<String> = _nameDisplay
    val isImportant: StateFlow<Boolean> = _isImportant
    val pageTitle: StateFlow<String> = _pageTitle
    val effectStream: SharedFlow<EditPageEffect> = _effectStream
}