package com.hamidjonhamidov.cvforkhamidjon.data_requests.api.achievments

import com.hamidjonhamidov.cvforkhamidjon.models.api.achievments.AchievementRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS.GET_ACHIEVEMENTS_PATH
import retrofit2.http.GET


interface AchievmentsApiService {

    @GET(GET_ACHIEVEMENTS_PATH)
    suspend fun getAchievementsSync(): List<AchievementRemoteModel>

}