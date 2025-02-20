package com.greenfieldxd.easybalance

import android.app.Application
import com.greenfieldxd.easybalance.transactions.di.initKoin
import org.koin.android.ext.koin.androidContext

class EasyBalanceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@EasyBalanceApplication)
        }
    }
}