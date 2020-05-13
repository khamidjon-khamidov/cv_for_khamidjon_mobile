package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AboutMeRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.SkillRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Education
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
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

    val ABOUTME_REMOTE_MODEL = AboutMeRemoteModel(
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

    val ABOUTME_MODEL = AboutMeModel(
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


    val SKILL_REMOTE_MODEL1 = SkillRemoteModel(
        "id 1",
        "name 1",
        0,
        listOf(
            "element 1",
            "element 2"
        )
    )

    val SKILL_REMOTE_MODEL2 = SkillRemoteModel(
        "id 2",
        "name 2",
        1,
        listOf(
            "element 3",
            "element 4"
        )
    )

    val SKILL_REMOTE_MODEL3 = SkillRemoteModel(
        "id 3",
        "name 3",
        2,
        listOf(
            "element 4",
            "element 5"
        )
    )

    val SKILL_REMOTE_MODEL4 = SkillRemoteModel(
        "id 4",
        "name 4",
        3,
        listOf(
            "element 5",
            "element 6"
        )
    )

    val SKILL_REMOTE_MODEL5 = SkillRemoteModel(
        "id 5",
        "name 5",
        4,
        listOf(
            "element 7",
            "element 8"
        )
    )

    val SKILLS_REMOTE_MODEL_LIST =
        listOf(
            SKILL_REMOTE_MODEL1,
            SKILL_REMOTE_MODEL2,
            SKILL_REMOTE_MODEL3,
            SKILL_REMOTE_MODEL4,
            SKILL_REMOTE_MODEL5
        )

    val SKILL_MODEL1 = SkillModel(
        "id 1",
        "name 1",
        0,
        listOf(
            "element 1",
            "element 2"
        )
    )

    val SKILL_MODEL2 = SkillModel(
        "id 2",
        "name 2",
        1,
        listOf(
            "element 3",
            "element 4"
        )
    )

    val SKILL_MODEL3 = SkillModel(
        "id 3",
        "name 3",
        2,
        listOf(
            "element 4",
            "element 5"
        )
    )

    val SKILL_MODEL4 = SkillModel(
        "id 4",
        "name 4",
        3,
        listOf(
            "element 5",
            "element 6"
        )
    )

    val SKILL_MODEL5 = SkillModel(
        "id 5",
        "name 5",
        4,
        listOf(
            "element 7",
            "element 8"
        )
    )

    val SKILLS_MODEL_LIST =
        listOf(
            SKILL_MODEL1,
            SKILL_MODEL2,
            SKILL_MODEL3,
            SKILL_MODEL4,
            SKILL_MODEL5
        )
}














