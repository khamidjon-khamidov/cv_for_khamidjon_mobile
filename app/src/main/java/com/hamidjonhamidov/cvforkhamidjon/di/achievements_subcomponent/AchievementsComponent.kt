package com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.AchievmentsActivity
import dagger.Subcomponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AchievmentsScope
@Subcomponent(
    modules = [
        AchievementsViewModelFactoryModule::class,
        AchievementModuleAbstract::class,
        AchievmentsFragmentModule::class
    ]
)
interface AchievementsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AchievementsComponent
    }

    fun inject(achievmentsActivity: AchievmentsActivity)
}
