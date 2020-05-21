package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainJobsEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewDestEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.InboxManager
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.JobManager
import com.hamidjonhamidov.cvforkhamidjon.util.data_manager.UIMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.util.*
import kotlin.collections.HashMap

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseMainViewModel(
    var inboxManager: InboxManager<MainViewDestEvent> = InboxManager(),
    var jobManger:JobManager<MainJobsEvent> = JobManager()
): ViewModel(){

    private val TAG = "AppDebug"

    // **************** data channel *******************
    val dataChannel = ConflatedBroadcastChannel<DataState<MainViewState, MainStateEvent>>()

    // **************** viewState **********************
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    fun setViewState(newViewState: MainViewState) {
        _viewState.value = newViewState
    }
}