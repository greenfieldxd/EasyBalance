package com.greenfieldxd.easybalance.di

import com.greenfieldxd.easybalance.data.repository.CategoryRepository
import com.greenfieldxd.easybalance.data.repository.CategoryRepositoryImpl
import com.greenfieldxd.easybalance.domain.usecase.FilteredTransactionsUseCase
import com.greenfieldxd.easybalance.domain.usecase.FilteredTransactionsUseCaseImpl
import com.greenfieldxd.easybalance.domain.usecase.GetAnalyticsUseCase
import com.greenfieldxd.easybalance.domain.usecase.GetAnalyticsUseCaseImpl
import com.greenfieldxd.easybalance.domain.usecase.TransactionClassifierUseCase
import com.greenfieldxd.easybalance.domain.usecase.TransactionClassifierUseCaseImpl
import com.greenfieldxd.easybalance.presentation.analytics.AnalyticsScreenModel
import com.greenfieldxd.easybalance.presentation.category.CategoryScreenModel
import com.greenfieldxd.easybalance.presentation.transactions.EditTransitionScreenModel
import com.greenfieldxd.easybalance.presentation.settings.SettingsScreenModel
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
    singleOf(::FilteredTransactionsUseCaseImpl).bind<FilteredTransactionsUseCase>()
    singleOf(::GetAnalyticsUseCaseImpl).bind<GetAnalyticsUseCase>()

    //Screen
    factory { TransactionScreenModel(get(), get(), get(), get()) }
    factory { EditTransitionScreenModel(get(), get()) }
    factory { CategoryScreenModel(get()) }
    factory { AnalyticsScreenModel(get()) }
    factory { SettingsScreenModel(get(), get(), get()) }
}