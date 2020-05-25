package com.hamidjonhamidov.cvforkhamidjon.fragment_builders.achievment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent.AchievmentsScope
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.a_android.AndroidFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.b_algorithms.AlgorithmsFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.c_others.OthersFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail.DetailsFragment
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
@AchievmentsScope
class AchievementsFragmentFactory
@Inject
constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val glideManager: GlideManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when(className){

            AndroidFragment::class.java.name -> {
                AndroidFragment(viewModelProviderFactory, glideManager)
            }

            AlgorithmsFragment::class.java.name -> {
                AlgorithmsFragment(viewModelProviderFactory, glideManager)
            }

            OthersFragment::class.java.name -> {
                OthersFragment(viewModelProviderFactory, glideManager)
            }

            DetailsFragment::class.java.name -> {
                DetailsFragment(glideManager)
            }

            else -> super.instantiate(classLoader, className)
        }
}






















