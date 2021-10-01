package com.hardiksachan.mvvmtodolist.data.data_source.local

import com.hardiksachan.mvvmtodolist.TaskEntityQueries
import com.hardiksachan.mvvmtodolist.common.IDispatcherProvider
import com.hardiksachan.mvvmtodolist.common.ResultWrapper
import com.hardiksachan.mvvmtodolist.data.data_source.local.mappers.toDomain
import com.hardiksachan.mvvmtodolist.domain.entity.Task
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalDataSourceImpl(
    private val taskEntityQueries: TaskEntityQueries,
    private val dispatcherProvider: IDispatcherProvider
) : ILocalDataSource {

    override fun getAllTasks(): Flow<ResultWrapper<Exception, List<Task>>> =
        taskEntityQueries
            .selectAll()
            .asFlow()
            .mapToList(dispatcherProvider.IO)
            .map { taskEntities ->
                ResultWrapper.build {
                    taskEntities.toDomain()
                }
            }
            .catch { e ->
                ResultWrapper.Failure(e)
            }
            .flowOn(dispatcherProvider.IO)


    override suspend fun getTaskWithId(id: String): ResultWrapper<Exception, Task> =
        ResultWrapper.build {
            withContext(dispatcherProvider.IO) {
                taskEntityQueries
                    .selectById(id)
                    .executeAsOne()
                    .toDomain()
            }
        }

    override suspend fun saveTask(task: Task) =
        task.run {
            withContext(dispatcherProvider.IO) {
                taskEntityQueries
                    .insertOrReplace(
                        id,
                        name,
                        important,
                        completed,
                        created
                    )
            }
        }

    override suspend fun deleteTask(task: Task) =
        withContext(dispatcherProvider.IO) {
            taskEntityQueries
                .deleteById(task.id)
        }
}