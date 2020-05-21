package com.hamidjonhamidov.cvforkhamidjon.ui.achievments

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hamidjonhamidov.cvforkhamidjon.MyApplication
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent.AchievementsComponent
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.AchievementsViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievementJobsEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievementsStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievementsViewDestEvent
import com.hamidjonhamidov.cvforkhamidjon.util.setupWithMyNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
abstract class BaseActivity : AppCompatActivity(){

    var currentNavController: LiveData<NavController>? = null

    val stateEvent: AchievementsStateEvent by lazy {
        object : AchievementsStateEvent {
            override val responsibleJob: AchievementJobsEvent
                get() = AchievementJobsEvent.GetAchievements()
            override val destinationView: AchievementsViewDestEvent
                get() = AchievementsViewDestEvent.AchievementsFragmentDest()
        }
    }
}