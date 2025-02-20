package com.greenfieldxd.easybalance.transactions.di

import com.greenfieldxd.easybalance.transactions.data.repository.CategoryRepository
import com.greenfieldxd.easybalance.transactions.data.repository.CategoryRepositoryImpl
import com.greenfieldxd.easybalance.transactions.domain.TransactionClassifierUseCase
import com.greenfieldxd.easybalance.transactions.domain.TransactionClassifierUseCaseImpl
import com.greenfieldxd.easybalance.transactions.presentation.TransactionScreenModel
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
    factory { TransactionScreenModel(getKoin().get(), getKoin().get()) }
}