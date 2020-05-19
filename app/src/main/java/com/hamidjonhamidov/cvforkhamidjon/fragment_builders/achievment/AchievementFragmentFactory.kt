package com.hamidjonhamidov.cvforkhamidjon.fragment_builders.achievment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent.AchievmentsScope
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.a_android.AndroidFragment
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import javax.inject.Inject

@AchievmentsScope
class AchievementFragmentFactory
@Inject
constructor(
    private val viewModelProviderFactory: ViewModelProvider.Factory,
    private val glideManager: GlideManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){

            AndroidFragment::class.java.name -> {
                // todo
                AndroidFragment()
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}






















