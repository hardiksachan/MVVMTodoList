package com.hardiksachan.mvvmtodolist.domain.repository

import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {

    fun getFilteredTasks(
        nameQuery: String = "",
        sortOrder: SortOrder = SortOrder.BY_DATE,
        hideCompleted: Boolean = false
    ): Flow<ResultWrapper<Exception, List<Task>>>

    suspend fun getTaskWithId(id: String): ResultWrapper<Exception, Task>

    suspend fun saveTask(task: Task)

    suspend fun deleteTask(task: Task)

}