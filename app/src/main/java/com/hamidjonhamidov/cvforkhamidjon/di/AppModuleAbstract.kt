package com.hamidjonhamidov.cvforkhamidjon.di

import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.RefreshLimitControllerImpl
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.RefreshLimitController
import dagger.Binds
import dagger.Module


@Module
abstract class AppModuleAbstract {

    // Makes Dagger provide SharedPreferencesStorage when a Storage type is requested
    @Binds
    abstract fun provideSharedPreferenceManager(storage: RefreshLimitControllerImpl): RefreshLimitController
}