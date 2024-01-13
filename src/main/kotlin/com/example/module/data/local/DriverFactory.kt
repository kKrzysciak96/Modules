package com.example.module.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.ModuleDb

class DriverFactory {
    fun createDriver(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(url = "jdbc:sqlite:ModuleDb.db", schema = ModuleDb.Schema)
        return driver
    }
}