package com.hardiksachan.mvvmtodolist.domain.repository

import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {

    fun getAllTasks(): Flow<ResultWrapper<Exception, List<Task>>>

    suspend fun getTaskWithId(id: String): ResultWrapper<Exception, Task>

    suspend fun saveTask(task: Task)

    suspend fun deleteTask(task: Task)

}