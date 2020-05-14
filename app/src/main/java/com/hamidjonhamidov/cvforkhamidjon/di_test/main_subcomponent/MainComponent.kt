package com.hamidjonhamidov.cvforkhamidjon.di_test.main_subcomponent

import android.app.Application
import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.main.MainNavHostFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@MainActivityScope
@Subcomponent(
    modules = [
        MainFragmentModule::class,
        MainModule::class,
        MainRepositoryModule::class,
        MainViewModelFactoryModule::class
    ]
)
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): MainComponent
    }


    // subcomponent to be injected from here
    fun inject(mainActivity: MainActivity)

    // inject this component to custom MainNavHostFragment to get fragmentFactory
    fun inject(mainNavHostFragment: MainNavHostFragment)
}