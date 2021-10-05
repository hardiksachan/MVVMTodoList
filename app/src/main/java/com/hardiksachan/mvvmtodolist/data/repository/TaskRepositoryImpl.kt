package com.hardiksachan.mvvmtodolist.data.repository

import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.data.data_source.local.ILocalDataSource
import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.hardiksachan.mvvmtodolist.domain.repository.ITaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val localDataSource: ILocalDataSource
) : ITaskRepository {

    override fun getFilteredTasks(
        nameQuery: String,
        sortOrder: SortOrder,
        hideCompleted: Boolean,
    ): Flow<ResultWrapper<Exception, List<Task>>> = when (sortOrder) {
        SortOrder.BY_NAME ->
            localDataSource.getFilteredTasksByName(nameQuery, hideCompleted)
        SortOrder.BY_DATE ->
            localDataSource.getFilteredTasksByDate(nameQuery, hideCompleted)
    }

    override suspend fun getTaskWithId(id: String): ResultWrapper<Exception, Task> =
        localDataSource.getTaskWithId(id)

    override suspend fun saveTask(task: Task) =
        localDataSource.saveTask(task)

    override suspend fun deleteTask(task: Task) =
        localDataSource.deleteTask(task)
}