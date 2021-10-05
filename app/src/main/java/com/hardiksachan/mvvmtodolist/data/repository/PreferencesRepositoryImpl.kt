package com.hardiksachan.mvvmtodolist.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hardiksachan.mvvmtodolist.common.IDispatcherProvider
import com.hardiksachan.mvvmtodolist.domain.constants.SortOrder
import com.hardiksachan.mvvmtodolist.domain.entity.FilterPreferences
import com.hardiksachan.mvvmtodolist.domain.repository.IPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

private const val TAG = "PreferencesRepositoryIm"

class PreferencesRepositoryImpl
@Inject
constructor(
    private val preferencesDataStore: DataStore<Preferences>,
    private val dispatcherProvider: IDispatcherProvider
) : IPreferencesRepository {

    override val filterPreferencesFlow: Flow<FilterPreferences> = preferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading prefs", exception)
            } else {
                throw exception
            }
        }
        .map { prefs ->
            val sortOrder = prefs[PreferencesKeys.SORT_ORDER]?.let { SortOrder.valueOf(it) }
                ?: SortOrder.BY_DATE
            val hideCompleted = prefs[PreferencesKeys.HIDE_COMPLETED] ?: false

            FilterPreferences(
                sortOrder,
                hideCompleted
            )
        }
        .flowOn(dispatcherProvider.IO)

    override suspend fun updateSortOrder(sortOrder: SortOrder) {
        withContext(dispatcherProvider.IO) {
            preferencesDataStore.edit { preferences ->
                preferences[PreferencesKeys.SORT_ORDER] = sortOrder.name
            }
        }
    }

    override suspend fun updateHideCompleted(hideCompleted: Boolean) {
        withContext(dispatcherProvider.IO) {
            preferencesDataStore.edit { preferences ->
                preferences[PreferencesKeys.HIDE_COMPLETED] = hideCompleted
            }
        }
    }

    private object PreferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val HIDE_COMPLETED = booleanPreferencesKey("hide_completed")
    }
}