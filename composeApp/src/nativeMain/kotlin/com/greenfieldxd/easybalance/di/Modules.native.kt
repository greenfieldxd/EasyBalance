package com.greenfieldxd.easybalance.di

import com.greenfieldxd.easybalance.data.database.DatabaseDriverFactory
import com.greenfieldxd.easybalance.data.database.TransactionDao
import databases.Database
import org.koin.dsl.module

actual val platformModule = module {
    single { DatabaseDriverFactory().create() }
    single { TransactionDao(Database(driver = get())) }
    single { TransactionDao(Database(driver = get())) }
}