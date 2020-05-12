package com.hamidjonhamidov.cvforkhamidjon.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
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
