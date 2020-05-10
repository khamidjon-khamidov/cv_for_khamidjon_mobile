package com.hamidjonhamidov.cvforkhamidjon.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.MainViewModel
import com.hamidjonhamidov.cvforkhamidjon.util.GlideManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment
constructor(
        private val viewModelFactory: ViewModelProvider.Factory,
        private val requestManager: GlideManager
) : Fragment(R.layout.fragment_home) {

//    val viewModel: MainViewModel by activityViewModels {
//        viewModelFactory
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
