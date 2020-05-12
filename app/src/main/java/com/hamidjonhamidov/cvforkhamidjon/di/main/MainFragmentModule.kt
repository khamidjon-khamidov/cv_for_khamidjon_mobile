package com.hamidjonhamidov.cvforkhamidjon.di.main

import androidx.fragment.app.FragmentFactory
import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.main.MainFragmentFactory
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
object MainFragmentModule {

    @JvmStatic
    @MainScope
    @Provides
    fun provideMainFragmentFactory(
        viewModelFactory: ViewModelProviderFactory,
        glideManager: GlideManager
    )
            : FragmentFactory  =
        MainFragmentFactory(viewModelFactory, glideManager)
}