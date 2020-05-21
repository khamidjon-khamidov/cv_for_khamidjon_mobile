package com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent

import androidx.fragment.app.FragmentFactory
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.achievments.AchievmentsApiService
import com.hamidjonhamidov.cvforkhamidjon.fragment_builders.achievment.AchievementsFragmentFactory
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.viewmodelfactory.AchievementsViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Retrofit

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@Module
object AchievmentsFragmentModule {

    @JvmStatic
    @AchievmentsScope
    @Provides
    fun provideMainFragmentFactory(
        achievementsViewModelFactory: AchievementsViewModelFactory,
        glideManager: GlideManager
    )
            : FragmentFactory =
        AchievementsFragmentFactory(achievementsViewModelFactory, glideManager)
}