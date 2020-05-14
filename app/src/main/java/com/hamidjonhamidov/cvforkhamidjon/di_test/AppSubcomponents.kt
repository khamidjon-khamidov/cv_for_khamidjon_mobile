package com.hamidjonhamidov.cvforkhamidjon.di_test

import com.hamidjonhamidov.cvforkhamidjon.di_test.main_subcomponent.MainComponent
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module(
    subcomponents = [
        MainComponent::class
    ]
)
class AppSubcomponents