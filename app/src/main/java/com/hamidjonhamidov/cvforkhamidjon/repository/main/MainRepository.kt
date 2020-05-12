package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.StateEvent
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAboutMe(stateEvent: StateEvent, isNetworkAvailable: Boolean): Flow<DataState<MainViewState>>

    fun getMySkills(stateEvent: StateEvent, isNetworkAvailable: Boolean): Flow<DataState<MainViewState>>

    fun getAchievements(stateEvent: StateEvent, isNetworkAvailable: Boolean): Flow<DataState<MainViewState>>

    fun getProjects(stateEvent: StateEvent, isNetworkAvailable: Boolean): Flow<DataState<MainViewState>>


}