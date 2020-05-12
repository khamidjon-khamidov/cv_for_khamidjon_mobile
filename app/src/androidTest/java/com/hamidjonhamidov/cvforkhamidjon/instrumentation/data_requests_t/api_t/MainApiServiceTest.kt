package com.hamidjonhamidov.cvforkhamidjon.instrumentation.data_requests_t.api_t

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AboutMeRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.util.constants.API_URLS.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
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
        withContext(Dispatchers.IO) {
//            val aboutMe: AboutMeRemoteModel = SUT.getAboutMeSync()
        }
//        assertEquals(aboutMe, AboutMeRemoteModel())
    }

    @After
    fun close() {

    }
}




















