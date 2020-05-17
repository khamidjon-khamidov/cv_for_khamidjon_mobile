package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state

import kotlin.reflect.KClass

sealed class MainStateEvent {

    class GetHome: MainStateEvent()

    class GetAboutMe : MainStateEvent()

    class GetMySkills : MainStateEvent()

    class GetAchievements : MainStateEvent()

    class GetProjects : MainStateEvent()
}
















