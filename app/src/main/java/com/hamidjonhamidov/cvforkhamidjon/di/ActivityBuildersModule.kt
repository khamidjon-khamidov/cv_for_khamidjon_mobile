package com.hamidjonhamidov.cvforkhamidjon.di

import com.hamidjonhamidov.cvforkhamidjon.di.main.*
import com.hamidjonhamidov.cvforkhamidjon.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class ActivityBuildersModule{

    @MainScope
    @ContributesAndroidInjector(
        modules = [
            MainModule::class,
//            MainFragmentModule::class,
            MainNavHostModule::class,
            MainViewModelModule::class,
            MainRepositoryModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}