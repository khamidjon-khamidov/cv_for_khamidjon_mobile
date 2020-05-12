package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state

import com.hamidjonhamidov.cvforkhamidjon.util.StateEvent

sealed class MainStateEvent: StateEvent {

    class GetHome(): MainStateEvent(){

        override fun toString(): String {
            return "GetHome"
        }
    }

    class GetAboutMe() : MainStateEvent() {
        override fun toString(): String {
            return "GetAboutMe"
        }
    }

    class GetMySkills(): MainStateEvent(){
        override fun toString(): String {
            return "GetMySkills"
        }
    }

    class GetAchievements(): MainStateEvent(){
        override fun toString(): String {
            return "GetAchievements"
        }
    }

    class GetProjects(): MainStateEvent(){
        override fun toString(): String {
            return "GetProjects"
        }
    }
}