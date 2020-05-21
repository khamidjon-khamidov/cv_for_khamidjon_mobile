package com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievementJobsEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievementsStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievementsViewDestEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievementsViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.InboxManager
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.JobManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel


@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseAchievmentsViewModel(
    var inboxManager: InboxManager<AchievementsViewDestEvent> = InboxManager(),
    var jobManger: JobManager<AchievementJobsEvent> = JobManager()
): ViewModel(){

    private val TAG = "AppDebug"

    // **************** data channel *******************
    val dataChannel =
        ConflatedBroadcastChannel<DataState<AchievementsViewState, AchievementsStateEvent>>()

    // **************** viewState **********************
    private val _viewState: MutableLiveData<AchievementsViewState> = MutableLiveData()

    val viewState: LiveData<AchievementsViewState>
        get() = _viewState

    fun setViewState(newViewState: AchievementsViewState) {
        _viewState.value = newViewState
    }
}