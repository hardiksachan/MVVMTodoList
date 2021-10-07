package com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardiksachan.mvvmtodolist.common.IDispatcherProvider
import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder
import com.hardiksachan.mvvmtodolist.domain.entity.FilterPreferences
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.domain.repository.IPreferencesRepository
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
    private val prefsRepository: IPreferencesRepository,
    private val dispatcherProvider: IDispatcherProvider
) : ViewModel() {

    fun onEvent(event: TasksPageEvent) = when (event) {
        is TasksPageEvent.SearchQueryChanged -> handleSearchQueryChanged(query = event.newQuery)
        is TasksPageEvent.TaskCheckedChanged -> handleTaskChecked(
            task = event.task,
            completed = event.checked
        )
        is TasksPageEvent.SortByRequested -> handleSortByRequested(order = event.sortOrder)
        TasksPageEvent.SortMenuDismissed -> handleSortMenuDismissed()
        TasksPageEvent.SortMenuToggled -> handleSortMenuToggled()
        TasksPageEvent.DeleteCompletedTasksRequested -> handleDeleteCompletedTasksRequested()
        TasksPageEvent.HideOptionsMenuDismissed -> handleHideOptionsMenuDismissed()
        TasksPageEvent.HideOptionsMenuToggled -> handleHideOptionsMenuToggled()
        TasksPageEvent.ShowCompletedToggled -> handleShowCompletedToggled()
    }

    private fun handleShowCompletedToggled() {
        viewModelScope.launch {
            prefsRepository.updateHideCompleted(filterPreferences.value.hideCompleted.not())
        }
    }

    private fun handleHideOptionsMenuToggled() {
        viewModelScope.launch {
            _hideOptionsMenuVisible.emit(_hideOptionsMenuVisible.value.not())
        }
    }

    private fun handleHideOptionsMenuDismissed() {
        viewModelScope.launch {
            _hideOptionsMenuVisible.emit(false)
        }
    }

    private fun handleDeleteCompletedTasksRequested() {
        // TODO
    }

    private fun handleSortMenuToggled() {
        viewModelScope.launch {
            _sortMenuVisible.emit(_sortMenuVisible.value.not())
        }
    }

    private fun handleSortMenuDismissed() {
        viewModelScope.launch {
            _sortMenuVisible.emit(false)
        }
    }

    private fun handleSortByRequested(order: SortOrder) {
        viewModelScope.launch {
            prefsRepository.updateSortOrder(order)
            _sortMenuVisible.emit(false)
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
    private val _searchDisplay = MutableStateFlow("")
    private val _sortMenuVisible = MutableStateFlow(false)
    private val _hideOptionsMenuVisible = MutableStateFlow(false)

    // FOR UI STATE (PUBLIC)
    val filterPreferences: StateFlow<FilterPreferences> =
        prefsRepository.filterPreferencesFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500L),
            initialValue = FilterPreferences(
                sortOrder = SortOrder.BY_DATE,
                hideCompleted = false
            )
        )
    val tasks: StateFlow<List<Task>> =
        combine(
            _searchDisplay,
            filterPreferences,
        ) { searchQuery, filterPrefs ->
            searchQuery to filterPrefs
        }.flatMapLatest { (searchQuery, filterPrefs) ->
            taskRepository.getFilteredTasks(
                nameQuery = searchQuery,
                sortOrder = filterPrefs.sortOrder,
                hideCompleted = filterPrefs.hideCompleted
            ).map { result ->
                Log.d(TAG, "taskResult: $result")
                when (result) {
                    is ResultWrapper.Failure -> {
                        // todo: handle error case
                        emptyList()
                    }
                    is ResultWrapper.Success -> result.result
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500L),
            initialValue = emptyList()
        )
    val searchDisplay: StateFlow<String> = _searchDisplay
    val sortMenuVisible: StateFlow<Boolean> = _sortMenuVisible
    val hideOptionsMenuVisible: StateFlow<Boolean> = _hideOptionsMenuVisible
}