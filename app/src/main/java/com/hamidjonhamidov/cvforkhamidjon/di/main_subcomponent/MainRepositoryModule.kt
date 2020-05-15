package com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepository
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object MainRepositoryModule {

    @JvmStatic
    @MainActivityScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): MainApiService =
        retrofitBuilder
            .build()
            .create(MainApiService::class.java)


    @JvmStatic
    @MainActivityScope
    @Provides
    fun provideMainRepository(
        apiService: MainApiService,
        appDatabase: AppDatabase
    ): MainRepository =

        MainRepositoryImpl(apiService, appDatabase)
}