package com.hamidjonhamidov.cvforkhamidjon.repository

import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetwrokConstants.NETWORK_ERROR_TIMEOUT
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetwrokConstants.NETWORK_TIMEOUT
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetwrokConstants.UNKNOWN_ERROR
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import retrofit2.Response

@RunWith(JUnit4::class)
class RepositoryTest {

    lateinit var SUT: Repository

    @Before
    fun setUp() {
        SUT = Repository()
    }

    @Test
    fun safeApiCall_success_ApiResultSuccesReturned() = runBlocking {
        // arrange
        val someFun = suspend {
            "Hello, I am Khamidjon"
        }
        // act
        val response = SUT.safeApiCall(Dispatchers.IO) {
            someFun.invoke()
        }

        // assert
        assertEquals(true, response is ApiResult.Success<String?>)
        response as ApiResult.Success
        assertEquals("Hello, I am Khamidjon", response.value)
    }

    @Test
    fun safeApiCall_timeoutException_assertGenericError() = runBlocking {
        // arrange
        val someFun = suspend {
            withTimeout(1) {
                delay(1000)
            }
        }

        // act
        val response = SUT.safeApiCall(Dispatchers.IO) {
            someFun.invoke()
        }

        // act
        assertEquals(true, response is ApiResult.GenericError)
        response as ApiResult.GenericError
        assertEquals(response.code, 408)
        assertEquals(response.errorMessage, NETWORK_ERROR_TIMEOUT)
    }

    @Test
    fun safeApiCall_httpException_assertGenericError() = runBlocking {
        // arrange
        val responseError =
            Response
                .error<String>(
                    404,
                    ResponseBody.create(null, "Some Error")
                )

        val someFun = suspend {
            throw HttpException(responseError)
        }

        // act
        val response = SUT.safeApiCall(Dispatchers.IO) {
            someFun.invoke()
        }

        // act
        assertEquals(true, response is ApiResult.GenericError)
        response as ApiResult.GenericError
        assertEquals(response.code, 404)
    }

    @Test
    fun safeApiCall_anyException_assertGenericError() = runBlocking {
        // arrange
        val someFun = suspend {
            throw Exception()
        }

        // act
        val response = SUT.safeApiCall(Dispatchers.IO) {
            someFun.invoke()
        }

        // act
        assertEquals(true, response is ApiResult.GenericError)
        response as ApiResult.GenericError
        assertEquals(response.code, null)
        assertEquals(response.errorMessage, UNKNOWN_ERROR)
    }
}

















