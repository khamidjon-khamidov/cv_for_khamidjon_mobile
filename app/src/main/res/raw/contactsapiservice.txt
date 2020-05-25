package com.hamidjonhamidov.cvforkhamidjon.data_requests.api.contacts

import com.hamidjonhamidov.cvforkhamidjon.util.constants.SOME_CONSTANTS.MYURL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ContactsApiService {

    @GET(MYURL)
    suspend fun sendMessage(@Query("text") message: String): Response<Any>
}
