package com.hamidjonhamidov.cvforkhamidjon.di_test.main_subcomponent

import androidx.fragment.app.FragmentFactory
import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.main.MainFragmentFactory
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.MainViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
object MainFragmentModule {

    @JvmStatic
    @MainActivityScope
    @Provides
    fun provideMainFragmentFactory(
        mainViewModelFactory: MainViewModelFactory,
        glideManager: GlideManager
    )
            : FragmentFactory =
        MainFragmentFactory(mainViewModelFactory, glideManager)
}