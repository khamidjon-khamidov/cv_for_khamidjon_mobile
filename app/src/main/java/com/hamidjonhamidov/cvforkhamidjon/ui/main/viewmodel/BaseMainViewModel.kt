package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseMainViewModel(): ViewModel(){

    val dataChannel = ConflatedBroadcastChannel<DataState<MainViewState>>()

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val messages = HashMap<String, MutableLiveData<Message?>>()

    fun setViewState(newViewState: MainViewState) {
        _viewState.value = newViewState
    }
}