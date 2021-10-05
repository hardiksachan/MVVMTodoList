package com.hardiksachan.mvvmtodolist.domain.repository

import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder
import com.hardiksachan.mvvmtodolist.domain.entity.FilterPreferences
import kotlinx.coroutines.flow.Flow

interface IPreferencesRepository {
    val filterPreferencesFlow: Flow<FilterPreferences>

    suspend fun updateSortOrder(sortOrder: SortOrder)

    suspend fun updateHideCompleted(hideCompleted: Boolean)
}