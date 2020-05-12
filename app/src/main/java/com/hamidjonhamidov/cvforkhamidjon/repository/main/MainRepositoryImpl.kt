package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.di.main.MainScope
import javax.inject.Inject

@MainScope
class MainRepositoryImpl
@Inject
constructor(
    apiService: MainApiService,
    appDatabase: AppDatabase
)
    : MainRepository {}