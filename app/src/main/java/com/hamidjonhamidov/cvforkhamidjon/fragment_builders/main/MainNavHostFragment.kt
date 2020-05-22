package com.hamidjonhamidov.cvforkhamidjon.fragment_builders.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainNavHostFragment : NavHostFragment() {

    private val TAG = "AppDebug"

    @Inject
    lateinit var mainFragmentFactory: FragmentFactory


    @FlowPreview
    override fun onAttach(context: Context) {
        (activity as MainActivity)
            .mainComponent
            .inject(this)

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = mainFragmentFactory
        super.onCreate(savedInstanceState)
    }
}
