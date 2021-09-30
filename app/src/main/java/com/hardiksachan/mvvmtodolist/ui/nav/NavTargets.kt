package com.hardiksachan.mvvmtodolist.ui.nav

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object NavTargets {

    val viewTasks = object : INavTarget {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()

        override val destination: String
            get() = "view_tasks"
    }

    object AddEditTask {
        const val KEY_TASK_ID = "taskId"
        private const val ROUTE_NAME = "add_edit_task"
        const val route = "$ROUTE_NAME?$KEY_TASK_ID={$KEY_TASK_ID}"
        val argumentsList = listOf(
            navArgument(KEY_TASK_ID) {
                type = NavType.StringType
                nullable = true
            }
        )

        val addTask = object : INavTarget {
            override val arguments: List<NamedNavArgument>
                get() = argumentsList
            override val destination: String
                get() = ROUTE_NAME

        }

        fun editTask(
            taskId: String
        ) = object : INavTarget {
            override val arguments: List<NamedNavArgument>
                get() = argumentsList
            override val destination: String
                get() = "$ROUTE_NAME/$KEY_TASK_ID={$taskId}"

        }
    }

}