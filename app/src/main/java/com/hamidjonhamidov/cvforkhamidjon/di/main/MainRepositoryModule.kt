package com.hamidjonhamidov.cvforkhamidjon.di.main

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.repository.Repository
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepository
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object MainRepositoryModule {

    @JvmStatic
    @MainScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): MainApiService =
        retrofitBuilder
            .build()
            .create(MainApiService::class.java)

    @JvmSuppressWildcards
    @MainScope
    @Provides
    fun getBaseRepository() = Repository()

    @JvmStatic
    @MainScope
    @Provides
    fun provideMainRepository(
        apiService: MainApiService,
        appDatabase: AppDatabase,
        repository: Repository
    ): MainRepository =

        MainRepositoryImpl(apiService, appDatabase, repository)
}