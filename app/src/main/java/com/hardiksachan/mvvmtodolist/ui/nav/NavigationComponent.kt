package com.hardiksachan.mvvmtodolist.ui.nav

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hardiksachan.mvvmtodolist.presentation_logic.page_add_edit_task.EditPageEffect
import com.hardiksachan.mvvmtodolist.presentation_logic.page_add_edit_task.EditPageEvent
import com.hardiksachan.mvvmtodolist.presentation_logic.page_add_edit_task.EditPageViewModel
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksPageEffect
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksPageEvent
import com.hardiksachan.mvvmtodolist.presentation_logic.page_view_tasks.TasksViewModel
import com.hardiksachan.mvvmtodolist.ui.page_add_edit_task.AddEditTaskPage
import com.hardiksachan.mvvmtodolist.ui.page_view_tasks.TasksPage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@ExperimentalMaterialApi
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

            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                vm.effectStream.onEach {
                    when (it) {
                        is TasksPageEffect.ShowUndoDeleteTaskMessage -> {
                            val snackbarResult = snackbarHostState.showSnackbar(
                                message = "Task Deleted",
                                actionLabel = "Undo"
                            )
                            when (snackbarResult) {
                                SnackbarResult.Dismissed -> {
                                }
                                SnackbarResult.ActionPerformed -> {
                                    vm.onEvent(TasksPageEvent.UndoDeleteTask(task = it.task))
                                }
                            }
                        }
                        TasksPageEffect.NavigateToAddTask -> navigator.navigateTo(NavTargets.AddEditTask.addTask)
                        is TasksPageEffect.NavigateToEditTask -> navigator.navigateTo(
                            NavTargets.AddEditTask.editTask(
                                it.task.id
                            )
                        )
                    }
                }.launchIn(this)
            }

            TasksPage(
                tasks = tasks.value,
                searchDisplay = searchDisplay.value,
                sortMenuVisible = sortMenuVisible.value,
                hideOptionsMenuVisible = hideOptionsMenuVisible.value,
                showCompleted = showCompleted.value,
                snackbarHostState = snackbarHostState,
                onEvent = { vm.onEvent(it) }
            )
        }

        composable(NavTargets.AddEditTask.route) { entry ->
            val taskId = entry.arguments
                ?.getString(NavTargets.AddEditTask.KEY_TASK_ID)
                ?.drop(1)
                ?.dropLast(1)

            val vm: EditPageViewModel = hiltViewModel()

            LaunchedEffect(Unit) {
                taskId?.let {
                    vm.onEvent(EditPageEvent.InitWithTask(it))
                }

                vm.effectStream.onEach {
                    when (it) {
                        EditPageEffect.NavigateToListPage -> navigator.navigateTo(NavTargets.viewTasks)
                    }
                }.launchIn(this)
            }

            val appBarTitle = vm.pageTitle.collectAsState()
            val taskName = vm.nameDisplay.collectAsState()
            val isImportant = vm.isImportant.collectAsState()

            AddEditTaskPage(
                appBarTitle = appBarTitle.value,
                taskName = taskName.value,
                isImportant = isImportant.value,
                createdOn = "to be initialised",
                onCheckedChange = {
                    vm.onEvent(EditPageEvent.IsImportantToggled)
                },
                onSubmit = {
                    vm.onEvent(EditPageEvent.SaveAndExit)
                },
                onTaskNameChanged = {
                    vm.onEvent(EditPageEvent.NameChanged(it))
                }
            )
        }
    }
}