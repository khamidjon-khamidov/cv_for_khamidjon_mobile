package com.hamidjonhamidov.cvforkhamidjon.fragment_builders.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent.MainActivityScope
import com.hamidjonhamidov.cvforkhamidjon.ui.main.a_home.HomeFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainJobsEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewDestEvent
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@MainActivityScope
class MainFragmentFactory
@Inject
constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val glideManager: GlideManager
) : FragmentFactory() {

    @FlowPreview
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (className) {

            HomeFragment::class.java.name -> {
                HomeFragment(viewModelProviderFactory, glideManager,
                    object : MainStateEvent {
                        override val responsibleJob: MainJobsEvent
                            get() = MainJobsEvent.GetAboutMe()
                        override val destinationView: MainViewDestEvent
                            get() = MainViewDestEvent.HomeFragmentDest()
                    }
                )
            }

            else -> {
                super.instantiate(classLoader, className)
            }
        }
}