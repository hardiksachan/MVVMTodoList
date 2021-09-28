package com.hardiksachan.mvvmtodolist.domain.entity

import java.text.DateFormat

data class Task(
    val name: String,
    val important: Boolean = false,
    val completed: Boolean = false,
    val created: Long = System.currentTimeMillis()
) {
    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(created)
}
