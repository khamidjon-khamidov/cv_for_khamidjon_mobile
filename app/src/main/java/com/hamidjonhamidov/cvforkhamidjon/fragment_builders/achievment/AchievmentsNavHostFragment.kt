package com.hamidjonhamidov.cvforkhamidjon.fragment_builders.achievment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.AchievmentsActivity
import com.hamidjonhamidov.cvforkhamidjon.ui.main.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AchievmentsNavHostFragment : NavHostFragment() {

    private val TAG = "AppDebug"

    @Inject
    lateinit var achievmentsFragmentFragmentFactory: FragmentFactory


    @FlowPreview
    override fun onAttach(context: Context) {
        (activity as AchievmentsActivity)
            .achievementsComponent
            .inject(this)

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = achievmentsFragmentFragmentFactory
        super.onCreate(savedInstanceState)
    }
}