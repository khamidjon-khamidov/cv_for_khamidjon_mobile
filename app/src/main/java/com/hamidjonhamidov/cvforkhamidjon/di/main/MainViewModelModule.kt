package com.hamidjonhamidov.cvforkhamidjon.di.main

import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.cvforkhamidjon.di.ViewModeKey
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
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModeKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}