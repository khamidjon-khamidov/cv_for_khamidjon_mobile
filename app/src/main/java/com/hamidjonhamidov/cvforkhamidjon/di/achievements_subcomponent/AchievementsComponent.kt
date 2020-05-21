package com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.achievment.AchievmentsNavHostFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.AchievmentsActivity
import dagger.Subcomponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AchievmentsScope
@Subcomponent(
    modules = [
        AchievmentsFragmentModule::class,
        AchievementModuleAbstract::class,
        AchievementsModule::class,
        AchievementsViewModelFactoryModule::class
    ]
)
interface AchievementsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AchievementsComponent
    }

    // subcomponent to be injected from here
    fun inject(achievmentsActivity: AchievmentsActivity)

    fun inject(navhostFragment: AchievmentsNavHostFragment)
}
