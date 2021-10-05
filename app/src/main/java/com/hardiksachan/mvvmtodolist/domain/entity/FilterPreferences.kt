package com.hardiksachan.mvvmtodolist.domain.entity

import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder

data class FilterPreferences(
    val sortOrder: SortOrder,
    val hideCompleted: Boolean
)
