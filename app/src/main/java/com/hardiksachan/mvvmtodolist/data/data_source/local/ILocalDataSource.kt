package com.hardiksachan.mvvmtodolist.data.data_source.local

import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import kotlinx.coroutines.flow.Flow

interface ILocalDataSource {

    fun getFilteredTasksByName(
        nameQuery: String,
        hideCompleted: Boolean
    ): Flow<ResultWrapper<Exception, List<Task>>>

    fun getFilteredTasksByDate(
        nameQuery: String,
        hideCompleted: Boolean
    ): Flow<ResultWrapper<Exception, List<Task>>>

    suspend fun getTaskWithId(id: String): ResultWrapper<Exception, Task>

    suspend fun saveTask(task: Task)

    suspend fun deleteTask(task: Task)

}