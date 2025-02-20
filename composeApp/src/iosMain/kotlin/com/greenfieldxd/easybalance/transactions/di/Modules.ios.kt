package com.greenfieldxd.easybalance.transactions.di

import com.greenfieldxd.easybalance.transactions.data.database.DatabaseDriverFactory
import com.greenfieldxd.easybalance.transactions.data.database.TransactionDao
import databases.Database
import org.koin.dsl.module

actual val platformModule = module {
    single { TransactionDao(Database(driver = DatabaseDriverFactory().create())) }
}