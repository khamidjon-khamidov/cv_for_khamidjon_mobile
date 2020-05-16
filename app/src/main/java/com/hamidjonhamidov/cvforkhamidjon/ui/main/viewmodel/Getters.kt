package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.Message
import com.hamidjonhamidov.cvforkhamidjon.util.MyJob
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getCurrentViewStateOrNew(): MainViewState = viewState.value ?: MainViewState()


@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.isJobActive(mJob: MyJob): Boolean {
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobs.contains(mJob)
}


@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.areAnyJobsActive(): Boolean {
    val viewState = getCurrentViewStateOrNew()
    return viewState.activeJobs.isNotEmpty()
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.addToJobs(mJob: MyJob) {
    val update = getCurrentViewStateOrNew()
    update.activeJobs.add(mJob)
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.removeFromJobs(mJob: MyJob) {
    val update = getCurrentViewStateOrNew()
    update.activeJobs.remove(mJob)
    setViewState(update)
}


@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getMessage(toFragment: String): FragmentMessage? {
    return messages[toFragment]?.peek()
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getMessageAndSetInProgress(toFragment: String): FragmentMessage? {
    val message = messages[toFragment]?.peek()
    message?.progressStatus = FragmentMessage.MESSAGE_IN_PROGRESS
    return message
}

























