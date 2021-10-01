package com.hardiksachan.mvvmtodolist

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
class TaskDatabaseTest {

    private val inMemorySqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
        Database.Schema.create(this)
    }

    private val queries = Database(inMemorySqlDriver).taskEntityQueries

    @Test
    fun smokeTest() {
        val emptyItems = queries.selectAll().executeAsList()
        assertEquals(emptyItems.size, 0)

        queries.insertOrReplace(
            id = "id_001",
            name = "dummy name",
            important = false,
            completed = false,
            created = 5L
        )

        val items = queries.selectAll().executeAsList()
        assertEquals(items.size, 1)

        val task = queries.selectById("id_001").executeAsOneOrNull()
        assertEquals(task?.name, "dummy name")
        assertEquals(task?.important, false)
        assertEquals(task?.completed, false)
        assertEquals(task?.created, 5L)
    }
}