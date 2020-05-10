package com.hamidjonhamidov.cvforkhamidjon.di.main

import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.main.MainNavHostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class MainNavHostModule {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun navHostFragmentInjector(): MainNavHostFragment
}