package com.greenfieldxd.easybalance.di

import com.greenfieldxd.easybalance.data.repository.CategoryRepository
import com.greenfieldxd.easybalance.data.repository.CategoryRepositoryImpl
import com.greenfieldxd.easybalance.domain.TransactionClassifierUseCase
import com.greenfieldxd.easybalance.domain.TransactionClassifierUseCaseImpl
import com.greenfieldxd.easybalance.presentation.analytics.AnalyticsScreenModel
import com.greenfieldxd.easybalance.presentation.transactions.TransactionScreenModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    //Repository
    singleOf(::CategoryRepositoryImpl).bind<CategoryRepository>()

    //UseCase
    singleOf(::TransactionClassifierUseCaseImpl).bind<TransactionClassifierUseCase>()

    //Screen
    factory { TransactionScreenModel(get(), get()) }
    factory { AnalyticsScreenModel(get()) }
}