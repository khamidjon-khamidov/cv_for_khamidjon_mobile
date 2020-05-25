package com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.achievments.AchievmentsApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object AchievementsModule{

    @JvmStatic
    @AchievmentsScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): AchievmentsApiService =
        retrofitBuilder
            .build()
            .create(AchievmentsApiService::class.java)
}