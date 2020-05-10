package com.hamidjonhamidov.cvforkhamidjon.di

import android.app.Application
import com.hamidjonhamidov.cvforkhamidjon.BaseApplication
import com.hamidjonhamidov.cvforkhamidjon.di.main.MainFragmentModule
import com.hamidjonhamidov.cvforkhamidjon.di.main.MainRepositoryModule
import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.main.MainNavHostFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Singleton
@Component(
    modules = [
//        AndroidInjectionModule::class,
        InternalVariablesModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)
}