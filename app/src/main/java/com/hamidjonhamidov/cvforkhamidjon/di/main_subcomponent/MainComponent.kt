package com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.main.MainNavHostFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.main.MainActivity
import dagger.Subcomponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@MainActivityScope
@Subcomponent(
    modules = [
        MainFragmentModule::class,
        MainModuleAbstract::class,
        MainModule::class,
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