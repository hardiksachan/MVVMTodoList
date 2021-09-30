package com.hardiksachan.mvvmtodolist.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hardiksachan.mvvmtodolist.ui.page_add_edit_task.AddEditTaskPage
import com.hardiksachan.mvvmtodolist.ui.page_view_tasks.TasksPage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator
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
            // TODO: hook up viewModel
            TasksPage(tasks = listOf()) {
                navigator.navigateTo(NavTargets.AddEditTask.addTask) // TODO: remove
            }
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