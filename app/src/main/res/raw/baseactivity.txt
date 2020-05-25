package com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementJobsEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsViewDestEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

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