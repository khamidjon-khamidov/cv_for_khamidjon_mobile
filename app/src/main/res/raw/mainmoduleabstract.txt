package com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepository
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class MainModuleAbstract {

    @Binds
    abstract fun provideMainRepository(
        repository: MainRepositoryImpl
    )
            : MainRepository
}