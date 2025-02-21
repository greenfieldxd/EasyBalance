package com.greenfieldxd.easybalance.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import databases.Database

actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver = NativeSqliteDriver(Database.Schema, "Database")
}