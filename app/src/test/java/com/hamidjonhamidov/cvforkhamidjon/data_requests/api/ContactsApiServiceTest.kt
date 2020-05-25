package com.hamidjonhamidov.cvforkhamidjon.data_requests.api

import com.google.gson.GsonBuilder
import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.contacts.ContactsApiService
import com.hamidjonhamidov.cvforkhamidjon.util.constants.SOME_CONSTANTS.BASE_URL
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class ContactsApiServiceTest {

    lateinit var SUT: ContactsApiService

    val TEST_MESSAGE = """
        hello, 
        I want to show you something important.
        please understand meU+1F642
        or I will punish you
    """.trimIndent()

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
            .create(ContactsApiService::class.java)
    }

    // send message assert result is ok
    @Test
    fun sendMessage_resultOk_assertResult() = runBlocking{
        // arrange
        val message = TEST_MESSAGE

        // act
        val result = SUT.sendMessage(message)

        delay(1000)
        assertEquals(result.code(), 200)
        Unit
    }
}
