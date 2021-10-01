package com.hardiksachan.mvvmtodolist.data.data_source.local.mappers

import com.hardiksachan.mvvmtodolist.TaskEntity
import com.hardiksachan.mvvmtodolist.domain.entity.Task

fun TaskEntity.toDomain() = Task(
    name, important, completed, created, id
)

fun List<TaskEntity>.toDomain() = map { it.toDomain() }