package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state

import kotlin.reflect.KClass


sealed class MainJobs {


    class GetAboutMe: MainJobs()

    class GetMySkills: MainJobs()

    class GetAchievements: MainJobs()

    class GetProjects: MainJobs()
}





















