package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.di.main.MainScope
import com.hamidjonhamidov.cvforkhamidjon.repository.Repository
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.StateEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@MainScope
class MainRepositoryImpl
@Inject
constructor(
    apiService: MainApiService,
    appDatabase: AppDatabase,
    repository: Repository
)
    : MainRepository {
    override fun getAboutMe(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> {
        TODO("Not yet implemented")
    }

    override fun getMySkills(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> {
        TODO("Not yet implemented")
    }

    override fun getAchievements(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> {
        TODO("Not yet implemented")
    }

    override fun getProjects(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> {
        TODO("Not yet implemented")
    }

}