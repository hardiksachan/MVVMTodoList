package com.hardiksachan.mvvmtodolist.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksPageEvent
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksViewModel
import com.hardiksachan.mvvmtodolist.ui.page_add_edit_task.AddEditTaskPage
import com.hardiksachan.mvvmtodolist.ui.page_view_tasks.TasksPage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator,
) {
    LaunchedEffect("navigation") {
        navigator.pageStream.onEach {
            navController.navigate(it.destination)
        }.launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = NavTargets.viewTasks.destination
    ) {
        composable(NavTargets.viewTasks.destination) {

            val vm: TasksViewModel = hiltViewModel()

            val tasks = vm.tasks.collectAsState()
            val searchDisplay = vm.searchDisplay.collectAsState()
            val sortMenuVisible = vm.sortMenuVisible.collectAsState()
            val hideOptionsMenuVisible = vm.hideOptionsMenuVisible.collectAsState()
            val showCompleted = vm.filterPreferences.map { it.hideCompleted.not() }.collectAsState(
                initial = true
            )

            TasksPage(
                tasks = tasks.value,
                searchDisplay = searchDisplay.value,
                onSearchDisplayChanged = {
                    vm.onEvent(TasksPageEvent.SearchQueryChanged(it))
                },
                onAddButtonClicked = {
                    // TODO
                },
                onTaskCheckChanged = { task, checked ->
                    vm.onEvent(TasksPageEvent.TaskCheckedChanged(task, checked))
                },
                onSortMenuDismissRequest = {
                    vm.onEvent(TasksPageEvent.SortMenuDismissed)
                },
                onSortMenuClicked = {
                    vm.onEvent(TasksPageEvent.SortMenuToggled)
                },
                onSortByDateClicked = {
                    vm.onEvent(TasksPageEvent.SortByRequested(SortOrder.BY_DATE))
                }, onSortByNameClicked = {
                    vm.onEvent(TasksPageEvent.SortByRequested(SortOrder.BY_NAME))
                },
                sortMenuVisible =sortMenuVisible.value,
                hideOptionsMenuVisible = hideOptionsMenuVisible.value,
                onHideOptionsMenuClicked = {
                    vm.onEvent(TasksPageEvent.HideOptionsMenuToggled)
                },
                onHideOptionsMenuDismissRequest = {
                    vm.onEvent(TasksPageEvent.HideOptionsMenuDismissed)
                },
                showCompleted = showCompleted.value,
                onShowCompletedToggled = {
                    vm.onEvent(TasksPageEvent.ShowCompletedToggled)
                },
                onDeleteCompletedClicked = {
                    vm.onEvent(TasksPageEvent.DeleteCompletedTasksRequested)
                },
                onSearchDisplayClosed = {},
            )
        }

        composable(NavTargets.AddEditTask.route) { entry ->
            val taskId = entry.arguments?.getString(NavTargets.AddEditTask.KEY_TASK_ID)

            // TODO: hook up viewModel
            AddEditTaskPage(
                appBarTitle = "Add Task",
                taskName = "",
                isImportant = false,
                createdOn = "",
                onCheckedChange = {},
                onSubmit = {
                    navigator.navigateTo(NavTargets.viewTasks) // TODO: remove
                },
                onTaskNameChanged = {}
            )
        }
    }
}