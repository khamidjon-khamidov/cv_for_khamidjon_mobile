package com.hamidjonhamidov.cvforkhamidjon.di

import com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent.AchievementsComponent
import com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent.MainComponent
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module(
    subcomponents = [
        MainComponent::class,
        AchievementsComponent::class
    ]
)
class AppSubcomponents