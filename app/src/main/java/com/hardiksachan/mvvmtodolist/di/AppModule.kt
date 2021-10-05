package com.hardiksachan.mvvmtodolist.di

import android.app.Application
import android.content.Context
import com.hardiksachan.mvvmtodolist.Database
import com.hardiksachan.mvvmtodolist.TaskEntityQueries
import com.hardiksachan.mvvmtodolist.common.IDispatcherProvider
import com.hardiksachan.mvvmtodolist.common.ProductionDispatcherProvider
import com.hardiksachan.mvvmtodolist.data.data_source.local.ILocalDataSource
import com.hardiksachan.mvvmtodolist.data.data_source.local.LocalDataSourceImpl
import com.hardiksachan.mvvmtodolist.data.data_source.local.utils.createDatabaseDriver
import com.hardiksachan.mvvmtodolist.data.repository.PreferencesRepositoryImpl
import com.hardiksachan.mvvmtodolist.data.repository.TaskRepositoryImpl
import com.hardiksachan.mvvmtodolist.data.repository.preferencesDataStore
import com.hardiksachan.mvvmtodolist.domain.repository.IPreferencesRepository
import com.hardiksachan.mvvmtodolist.domain.repository.ITaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): Database = app.applicationContext.createDatabaseDriver()
        .run {
            Database(this)
        }

    @Provides
    @Singleton
    fun provideTaskQueries(
        db: Database
    ): TaskEntityQueries = db.taskEntityQueries

    @Provides
    @Singleton
    fun provideProductionDispatcherProvider(): IDispatcherProvider =
        ProductionDispatcherProvider

    @Provides
    @Singleton
    fun provideLocalDataSource(
        taskEntityQueries: TaskEntityQueries,
        dispatcherProvider: IDispatcherProvider
    ): ILocalDataSource = LocalDataSourceImpl(
        taskEntityQueries,
        dispatcherProvider
    )

    @Provides
    @Singleton
    fun provideTaskRepository(
        localDataSource: ILocalDataSource
    ): ITaskRepository = TaskRepositoryImpl(localDataSource)

    @Provides
    @Singleton
    fun providePreferencesRepository(
        @ApplicationContext context: Context,
        dispatcherProvider: IDispatcherProvider
    ): IPreferencesRepository = PreferencesRepositoryImpl(
        context.preferencesDataStore,
        dispatcherProvider
    )

}