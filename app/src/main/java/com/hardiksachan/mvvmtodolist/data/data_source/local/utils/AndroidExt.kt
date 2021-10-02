package com.hardiksachan.mvvmtodolist.data.data_source.local.utils

import android.content.Context
import com.hardiksachan.mvvmtodolist.Database
import com.squareup.sqldelight.android.AndroidSqliteDriver

fun Context.createDatabaseDriver() = AndroidSqliteDriver(
    schema = Database.Schema,
    context = this,
    name = DATABASE_NAME,
)