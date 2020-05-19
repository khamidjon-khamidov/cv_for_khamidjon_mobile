package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAboutMe(
        stateEvent: MainStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean = true
    )
            : Flow<DataState<MainViewState, MainStateEvent>>

    fun getMySkills(
        stateEvent: MainStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean = true
    )
            : Flow<DataState<MainViewState, MainStateEvent>>

    fun getProjects(
        stateEvent: MainStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean = true
    )
            : Flow<DataState<MainViewState, MainStateEvent>>

    fun getPosts(
        stateEvent: MainStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean = true
    )
            : Flow<DataState<MainViewState, MainStateEvent>>
}