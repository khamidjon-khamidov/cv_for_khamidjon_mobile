package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AboutMeRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AchievementRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.ProjectsRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.SkillRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.*
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

    val ACHIEVEMENT_REMOTE_MODEL1 = AchievementRemoteModel(
        1,
        "title 1",
        listOf(
            Honor(1, "title 1", "des 1", "link 1"),
            Honor(2, "title 2", "des 1", "link 1"),
            Honor(3, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_REMOTE_MODEL2 = AchievementRemoteModel(
        2,
        "title 2",
        listOf(
            Honor(4, "title 1", "des 1", "link 1"),
            Honor(5, "title 2", "des 1", "link 1"),
            Honor(6, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_REMOTE_MODEL3 = AchievementRemoteModel(
        3,
        "title 3",
        listOf(
            Honor(21, "title 1", "des 1", "link 1"),
            Honor(23, "title 2", "des 1", "link 1"),
            Honor(32, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_REMOTE_MODEL4 = AchievementRemoteModel(
        4,
        "title 4",
        listOf(
            Honor(32, "title 1", "des 1", "link 1"),
            Honor(21, "title 2", "des 1", "link 1"),
            Honor(12, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_REMOTE_MODEL5 = AchievementRemoteModel(
        5,
        "title 5",
        listOf(
            Honor(98, "title 1", "des 1", "link 1"),
            Honor(980, "title 2", "des 1", "link 1"),
            Honor(987, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_MODEL1 = AchievementModel(
        1,
        "title 1",
        listOf(
            Honor(1, "title 1", "des 1", "link 1"),
            Honor(2, "title 2", "des 1", "link 1"),
            Honor(3, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_MODEL2 = AchievementModel(
        2,
        "title 2",
        listOf(
            Honor(4, "title 1", "des 1", "link 1"),
            Honor(5, "title 2", "des 1", "link 1"),
            Honor(6, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_MODEL3 = AchievementModel(
        3,
        "title 3",
        listOf(
            Honor(21, "title 1", "des 1", "link 1"),
            Honor(23, "title 2", "des 1", "link 1"),
            Honor(32, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_MODEL4 = AchievementModel(
        4,
        "title 4",
        listOf(
            Honor(32, "title 1", "des 1", "link 1"),
            Honor(21, "title 2", "des 1", "link 1"),
            Honor(12, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_MODEL5 = AchievementModel(
        5,
        "title 5",
        listOf(
            Honor(98, "title 1", "des 1", "link 1"),
            Honor(980, "title 2", "des 1", "link 1"),
            Honor(987, "title 3", "des 1", "link 1")
        )
    )

    val ACHIEVEMENT_MODEL_LIST =
        listOf(
            ACHIEVEMENT_MODEL1,
            ACHIEVEMENT_MODEL2,
            ACHIEVEMENT_MODEL3,
            ACHIEVEMENT_MODEL4,
            ACHIEVEMENT_MODEL5
        )

    val ACHIEVEMENT_REMOTE_MODEL_LIST =
        listOf(
            ACHIEVEMENT_REMOTE_MODEL1,
            ACHIEVEMENT_REMOTE_MODEL2,
            ACHIEVEMENT_REMOTE_MODEL3,
            ACHIEVEMENT_REMOTE_MODEL4,
            ACHIEVEMENT_REMOTE_MODEL5
        )

    val PROJECT_REMOTE_MODEL1 = ProjectsRemoteModel(
        1,
        "time 1",
        "title 1",
        "des 1",
        "link 1",
        "gitlink 1"
    )

    val PROJECT_REMOTE_MODEL2 = ProjectsRemoteModel(
        2,
        "time 2",
        "title 2",
        "des 2",
        "link 2",
        "gitlink 2"
    )

    val PROJECT_REMOTE_MODEL3 = ProjectsRemoteModel(
        3,
        "time 3",
        "title 3",
        "des 3",
        "link 3",
        "gitlink 3"
    )

    val PROJECT_REMOTE_MODEL4 = ProjectsRemoteModel(
        4,
        "time 4",
        "title 4",
        "des 4",
        "link 4",
        "gitlink 4"
    )


    val PROJECT_MODEL1 = ProjectModel(
        1,
        "time 1",
        "title 1",
        "des 1",
        "link 1",
        "gitlink 1"
    )

    val PROJECT_MODEL2 = ProjectModel(
        2,
        "time 2",
        "title 2",
        "des 2",
        "link 2",
        "gitlink 2"
    )

    val PROJECT_MODEL3 = ProjectModel(
        3,
        "time 3",
        "title 3",
        "des 3",
        "link 3",
        "gitlink 3"
    )

    val PROJECT_MODEL4 = ProjectModel(
        4,
        "time 4",
        "title 4",
        "des 4",
        "link 4",
        "gitlink 4"
    )

    val PROJECT_MODEL_LIST =
        listOf(
            PROJECT_MODEL1,
            PROJECT_MODEL2,
            PROJECT_MODEL3,
            PROJECT_MODEL4
        )

    val PROJECT_REMOTE_MODEL_LIST =
        listOf(
            PROJECT_REMOTE_MODEL1,
            PROJECT_REMOTE_MODEL2,
            PROJECT_REMOTE_MODEL3,
            PROJECT_REMOTE_MODEL4
        )
}














