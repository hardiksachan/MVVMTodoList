package com.hardiksachan.mvvmtodolist.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
                sortMenuVisible = sortMenuVisible.value,
                hideOptionsMenuVisible = hideOptionsMenuVisible.value,
                showCompleted = showCompleted.value,
                onEvent = { vm.onEvent(it) }
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