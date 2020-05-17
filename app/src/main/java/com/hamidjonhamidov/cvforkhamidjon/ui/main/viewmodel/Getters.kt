package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.FragmentMessage.Companion.MESSAGE_IN_PROGRESS
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.FragmentMessage.Companion.MESSAGE_IS_NOT_IN_PROGRESS
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.MainJobs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getCurrentViewStateOrNew(): MainViewState = viewState.value ?: MainViewState()

private val TAG = "AppDebug"

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getLastMessage(toFragment: String): FragmentMessage? {
    return messages[toFragment]?.peek()
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getMessagesSize(toFragment: String): Int {
    return messages[toFragment]?.size ?: -1
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.isLastMessageInProgress(toFragment: String): Boolean {
    return (messages[toFragment]?.peek()?.progressStatus ?: MESSAGE_IS_NOT_IN_PROGRESS) == MESSAGE_IN_PROGRESS
}

























