package com.greenfieldxd.easybalance.di

import com.greenfieldxd.easybalance.data.database.DatabaseDriverFactory
import com.greenfieldxd.easybalance.data.database.TransactionDao
import databases.Database
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule = module {
    single { TransactionDao(Database(driver = DatabaseDriverFactory(context = androidContext()).create())) }
}