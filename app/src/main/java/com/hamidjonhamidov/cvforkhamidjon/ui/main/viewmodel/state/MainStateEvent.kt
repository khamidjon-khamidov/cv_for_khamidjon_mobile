package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state

import com.hamidjonhamidov.cvforkhamidjon.util.StateEvent

sealed class MainStateEvent : StateEvent {

    class GetHome() : MainStateEvent() {
        override val whichFragment: String
            get() = "GetHomeFragment"

        companion object{
            val toFragmentObj = "GetHome"
        }
    }

    class GetAboutMe() : MainStateEvent() {
        override val whichFragment: String
            get() = "GetAboutMeFragment"

        companion object{
            val toFragmentObj = "GetAboutMe"
        }
    }

    class GetMySkills() : MainStateEvent() {
        override val whichFragment: String
            get() = "GetMySkillsFragment"

        companion object{
            val toFragmentObj = "GetMySkills"
        }
    }

    class GetAchievements() : MainStateEvent() {
        override val whichFragment: String
            get() = "GetAchievementsFragment"

        companion object{
            val toFragmentObj = "GetAchievements"
        }
    }

    class GetProjects() : MainStateEvent() {
        override val whichFragment: String
            get() = "GetProjectsFragment"

        companion object{
            val toFragmentObj = "GetProjects"
        }
    }
}