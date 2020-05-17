package com.hamidjonhamidov.cvforkhamidjon.util


sealed class MainJobs {

    abstract val toFragment: String

    class GetAboutMe() : MainJobs(){

        override val toFragment: String
            get() = "GetHomeFragment"

        override fun toString(): String {
            return "GetAboutMeFragment"
        }
    }

    class GetMySkills: MainJobs(){
        override val toFragment: String
            get() = "GetMySkillsFragment"

        override fun toString(): String {
            return "GetMySkills"
        }
    }

    class GetAchiements: MainJobs(){

        override val toFragment: String
            get() = "GetAchievementsFragment"

        override fun toString(): String {
            return "GetAchievments"
        }
    }

    class GetProjects: MainJobs() {

        override val toFragment: String
            get() = "GetProjectsFragment"

        override fun toString(): String {
            return "GetProjects"
        }
    }
}





















