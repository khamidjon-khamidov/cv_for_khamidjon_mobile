package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

//@OptIn(FlowPreview::class)
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository
): ViewModel(){

    // todo
}

