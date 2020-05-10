package com.hamidjonhamidov.cvforkhamidjon.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.MainViewModel
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(vmFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}