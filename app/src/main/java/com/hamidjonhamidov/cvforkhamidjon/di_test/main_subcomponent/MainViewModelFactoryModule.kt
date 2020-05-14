package com.hamidjonhamidov.cvforkhamidjon.di_test.main_subcomponent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.di_test.ViewModelKey
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.MainViewModel
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class MainViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(vmFactoryMain: MainViewModelFactory): ViewModelProvider.Factory
}