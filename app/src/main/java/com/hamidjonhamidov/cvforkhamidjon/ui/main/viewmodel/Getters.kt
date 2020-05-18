package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

private val TAG = "AppDebug"

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getCurrentViewStateOrNew(): MainViewState = viewState.value ?: MainViewState()















