package com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main

import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AboutMeRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AchievementsRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.ProjectsRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.SkillRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS.GET_ABOUTME_PATH
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS.GET_ACHIEVEMENTS_PATH
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS.GET_PROJECTS_PATH
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS.GET_SKILLS_PATH
import retrofit2.http.GET

interface MainApiService{

    @GET(GET_ABOUTME_PATH)
    suspend fun getAboutMeSync(): List<AboutMeRemoteModel>

    @GET(GET_ACHIEVEMENTS_PATH)
    suspend fun getAchievementsSync(): List<AchievementsRemoteModel>

    @GET(GET_SKILLS_PATH)
    suspend fun getSkillsSync(): List<SkillRemoteModel>

    @GET(GET_PROJECTS_PATH)
    suspend fun getProjectsSync(): List<ProjectsRemoteModel>
}