package com.hamidjonhamidov.cvforkhamidjon.data_requests.api

import com.google.gson.GsonBuilder
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.ActualModelsInServer.ABOUTME0INSERVER
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.ActualModelsInServer.ACHIEVEMENT2INSERVER
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.ActualModelsInServer.POST0INSERVER
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.ActualModelsInServer.POST1INSERVER
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.ActualModelsInServer.PROJECT3INSERVER
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.ActualModelsInServer.SKILL7INSERVER
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.*
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


//@RunWith(MockitoJUnitRunner::class.java)
class MainApiServiceTest {

    lateinit var SUT: MainApiService

    @Before
    fun setUp() {
        val retrofitBuilder =
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory
                        .create(
                            GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create()
                        )
                )

        SUT = retrofitBuilder
            .build()
            .create(MainApiService::class.java)
    }

    @Test
    fun getAboutMeSync_aboutMe_returnAboutMe() = runBlocking {
        // arrange
        var aboutMeList: List<AboutMeRemoteModel> = listOf()

        // act
        withContext(Dispatchers.IO) {
            try {
                aboutMeList = SUT.getAboutMeSync()
            } catch (e: Exception) {
                assertEquals(e, "Nothing")
            }
        }

        // assert
        assertEquals(aboutMeList[0], ABOUTME0INSERVER)
    }

    @Test
    fun getAchievementsRemoteModel_requestAchievements_assertEqual() = runBlocking {
        // arrange
        var achievementsList = listOf<AchievementRemoteModel>()

        // act
        withContext(Dispatchers.IO) {
            try {
                achievementsList = SUT.getAchievementsSync()
            } catch (throwable: Throwable) {
                throw Exception(throwable.message)
            }
        }

        // assert
        assertEquals(achievementsList.size, 3)
        assertEquals(achievementsList[2], ACHIEVEMENT2INSERVER)
    }

    @Test
    fun getSkillsSync_skills_assertReturnedSkillEqualsOneInServer() = runBlocking {
        // arrange
        var skillsList = listOf<SkillRemoteModel>()

        // act
        withContext(Dispatchers.IO) {
            try {
                skillsList = SUT.getSkillsSync()
            } catch (throwable: Throwable) {
                throw Exception(throwable.message)
            }
        }

        // assert
        assertEquals(skillsList.size, 8)
        assertEquals(skillsList[7], SKILL7INSERVER)
    }

    @Test
    fun getProjectsSync_getFromRemoteServer_compareWithActual() = runBlocking {
        // arrange
        var projectsList = listOf<ProjectRemoteModel>()

        // act
        withContext(Dispatchers.IO) {
            try {
                projectsList = SUT.getProjectsSync()
            } catch (throwable: Throwable){
                throw Exception(throwable.message)
            }
        }

        // assert
        assertEquals(projectsList.size, 4)
        assertEquals(projectsList[3], PROJECT3INSERVER)
    }

    @Test
    fun getPostsSync_getFromRemoteServer_compareWithActual() = runBlocking {
        // arrange
        var postList = listOf<PostRemoteModel>()

        // act
        withContext(Dispatchers.IO) {
            try {
                postList = SUT.getPostsSync()
            } catch (throwable: Throwable){
                throw Exception(throwable.message)
            }
        }

        // assert
        assertEquals(postList.size, 18)
        assertEquals(postList[0], POST0INSERVER)
        assertEquals(postList[1], POST1INSERVER)
    }
}




















