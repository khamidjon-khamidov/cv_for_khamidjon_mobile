package com.hamidjonhamidov.cvforkhamidjon.repository.achievments

import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import kotlinx.coroutines.flow.Flow

interface AchievementsRepository {

    fun getAchievements(
        stateEvent: AchievementsStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean = true
    )
            : Flow<DataState<AchievementsViewState, AchievementsStateEvent>>
}