package com.hamidjonhamidov.cvforkhamidjon.di

import android.app.Application
import com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent.MainComponent
import com.hamidjonhamidov.cvforkhamidjon.util.NetworkConnection
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
@Component(
    modules = [
        InternalVariablesModule::class,
        AppModule::class,
        AppSubcomponents::class
    ]
)
interface AppComponent {

    val networkConnection: NetworkConnection

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
}