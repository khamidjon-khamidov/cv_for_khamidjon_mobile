package com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.di.ViewModelKey
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.AchievementsViewModel
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.AchievementsViewModelFactory
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class AchievementsViewModelFactoryModule {

    @FlowPreview
    @Binds
    @IntoMap
    @ViewModelKey(AchievementsViewModel::class)
    abstract fun bindMainViewModel(viewModel: AchievementsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(vmFactoryMain: AchievementsViewModelFactory): ViewModelProvider.Factory
}