package com.hamidjonhamidov.cvforkhamidjon.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
abstract class BaseMainFragment(
    fragmentId: Int,
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(fragmentId) {

    //    lateinit var mainUiCommunicationListener: MainUiCommunicationListener

    val viewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
    }

    abstract fun subscribeObservers()

    abstract fun initData()
}