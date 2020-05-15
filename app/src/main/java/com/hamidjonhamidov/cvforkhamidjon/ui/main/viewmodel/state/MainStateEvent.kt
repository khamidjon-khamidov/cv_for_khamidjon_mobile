package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state

import com.hamidjonhamidov.cvforkhamidjon.util.StateEvent

sealed class MainStateEvent : StateEvent {

    class GetHome() : MainStateEvent() {
        override val toFragment: String
            get() = "GetHome"
    }

    class GetAboutMe() : MainStateEvent() {
        override val toFragment: String
            get() = "GetAboutMe"
    }

    class GetMySkills() : MainStateEvent() {
        override val toFragment: String
            get() = "GetMySkills"
    }

    class GetAchievements() : MainStateEvent() {
        override val toFragment: String
            get() = "GetAchievements"
    }

    class GetProjects() : MainStateEvent() {
        override val toFragment: String
            get() = "GetProjects"
    }
}