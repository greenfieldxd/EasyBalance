package com.greenfieldxd.easybalance.transactions.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import databases.Database
import java.io.File

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        val appDir = File(System.getProperty("user.home"), "EasyBalance")
        val databasePath = File(appDir, "Database")

        if (!appDir.exists()) {
            appDir.mkdirs()
        }

        val driver = JdbcSqliteDriver("jdbc:sqlite:${databasePath.absolutePath}")

        if (!databasePath.exists()) {
            Database.Schema.create(driver)
        }

        return driver
    }
}