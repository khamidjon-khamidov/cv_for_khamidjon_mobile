package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.util.*
import kotlin.collections.HashMap

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseMainViewModel(): ViewModel(){

    private val TAG = "AppDebug"

    // **************** data channel *******************
    val dataChannel = ConflatedBroadcastChannel<DataState<MainViewState>>()

    // **************** viewState **********************
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    fun setViewState(newViewState: MainViewState) {
        _viewState.value = newViewState
    }

    // ***************** New Message Notifier *****************************
    private val _newMessageNotifier = MutableLiveData(false)

    val newMessageNotifier: LiveData<Boolean>
    get() = _newMessageNotifier

    fun notifyFragmentsWithNewMessage() {
        Log.d(TAG, "BaseMainViewModel: notifyFragmentsWithNewMessage: notified with new message")
        _newMessageNotifier.value = !(_newMessageNotifier.value ?: true)
    }

    val messages = HashMap<String, Queue<FragmentMessage>>()

    // ***************** Progress Bar Observer ****************************
    private val _progressBarStatus = HashMap<String, MutableLiveData<Boolean>>()

    fun getProgressBarObserver(whichFragment: String): LiveData<Boolean> {
        if(_progressBarStatus[whichFragment]==null){
            _progressBarStatus[whichFragment] = MutableLiveData()
        }

        return _progressBarStatus[whichFragment]!!
    }

    fun setProgressObserverVisibility(whichFragment: String, isVisible: Boolean){
        if(_progressBarStatus[whichFragment]==null){
            _progressBarStatus[whichFragment] = MutableLiveData()
        }
        _progressBarStatus[whichFragment]!!.value = isVisible
    }

    fun getProgressBarStatus(whichFragment: String): Boolean =
        _progressBarStatus[whichFragment]?.value ?: false

}