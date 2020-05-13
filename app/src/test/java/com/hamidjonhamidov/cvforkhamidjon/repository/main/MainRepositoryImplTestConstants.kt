package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AboutMeRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Education
import com.hamidjonhamidov.cvforkhamidjon.util.Message

object MainRepositoryImplTestConstants {

    val GETHOME = "GetHome"
    val GETABOUTME = "GetAboutMe"
    val GETMYSKILLS = "GetMySkills"
    val GETACHIEVEMENTS = "GetAchievements"
    val GETPROJECT = "GetProjects"

    val GETABOUTME_SUCCESS_MESSAGE = Message(
        "Success",
        "Successfully received from cache"
    )

    val ABOUTMEREMOTEMODEL = AboutMeRemoteModel(
        "45 April, 2334",
        "Tashlakskiy rayoni",
        "palonchi@gmail.com",
        "+99 34234 234234",
        listOf(
            Education("edu 1", "link1"),
            Education("edu 2", "link2")
        ),
        "nonsense picture link",
        "nonsense description"
    )

    val ABOUTMEMODEL = AboutMeModel(
        "45 April, 2334",
        "Tashlakskiy rayoni",
        "palonchi@gmail.com",
        "+99 34234 234234",
        listOf(
            Education("edu 1", "link1"),
            Education("edu 2", "link2")
        ),
        "nonsense picture link",
        "nonsense description"
    )

}