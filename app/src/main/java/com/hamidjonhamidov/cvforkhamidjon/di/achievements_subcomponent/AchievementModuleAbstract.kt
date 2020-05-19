package com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.repository.achievments.AchievementsRepositoryImpl
import com.hamidjonhamidov.cvforkhamidjon.repository.achievments.AchievementsRepository
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
abstract class AchievementModuleAbstract {

    @Binds
    abstract fun provideAchievementsRepository(
        repository: AchievementsRepositoryImpl
    )
            : AchievementsRepository
}