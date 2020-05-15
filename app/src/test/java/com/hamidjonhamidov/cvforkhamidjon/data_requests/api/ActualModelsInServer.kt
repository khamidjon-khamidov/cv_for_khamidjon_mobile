package com.hamidjonhamidov.cvforkhamidjon.data_requests.api

import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AboutMeRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AchievementsRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.ProjectsRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.SkillRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Education
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.Honor
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel

object ActualModelsInServer {

    val ABOUTME0INSERVER = AboutMeRemoteModel(
        "14 Sep, 1998",
        "133B Granville Road, London",
        "hamidovhamid1998@gmail.com",
        "+44 07565 336207",
        listOf(
            Education("TUIT", "https://tuit.uz/en"),
            Education("LSBU", "https://www.lsbu.ac.uk/")
        ),
        "https://firebasestorage.googleapis.com/v0/b/who-is-khamidjon.appspot.com/o/hamidjon_improved.png?alt=media&token=3d56b28a-c8aa-43fe-b739-78eb89eca601",
        "I am an enthusiastic Android and Junior Web Developer who is keen on learning and working with a friendly team.",
        "https://firebasestorage.googleapis.com/v0/b/who-is-khamidjon.appspot.com/o/cv%2FCV_Programming.docx?alt=media&token=0ab13a3a-9ad4-4741-a44d-700bb211cad9"
    )

    val ACHIEVEMENT2INSERVER = AchievementsRemoteModel(
        2003,
        "Others",
        listOf(
            Honor(3014, "Chess Regional Competition", "College Years", "https://firebasestorage.googleapis.com/v0/b/who-is-khamidjon.appspot.com/o/cv%2Fothers%2Fchess_certificate_modified.JPG?alt=media&token=c1808aa6-07a1-4caf-8d90-ccd6ff89b5dc"),
            Honor(3015, "English International Certificate", "IELTS", "https://firebasestorage.googleapis.com/v0/b/who-is-khamidjon.appspot.com/o/cv%2Fothers%2Fielts_certificate_modified.jpg?alt=media&token=4ab5607e-1b56-48df-b73e-d82686ee68b9")
        )
    )

    val PROJECT3INSERVER = ProjectsRemoteModel(
        7004,
        "June 2019",
        "English-Uzbek Dictionary",
        "This is my very first project which I created by far. It does not have ousanding features as it uses old SQL codes(not ROOM library). But, SQL database is secured with encryption. Despite this, it has 1K+ downloads in Google Play. In the future, I have plans to rewrite the whole code and monetize it.",
        "https://play.google.com/store/apps/details?id=com.hamidovhamidjondictionary.englishuzbekdictionary100k",
        ""
    )

    val SKILL7INSERVER = SkillRemoteModel(
        "skill1007",
        "Chess",
        80,
        listOf("Chess contests", "3rd place", "Play in free time")
    )
}