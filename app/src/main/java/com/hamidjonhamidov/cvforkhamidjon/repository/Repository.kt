package com.hamidjonhamidov.cvforkhamidjon.repository

import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult
import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult.GenericError
import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult.Success
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetwrokConstants.NETWORK_DELAY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetwrokConstants.NETWORK_ERROR_TIMEOUT
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetwrokConstants.NETWORK_TIMEOUT
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetwrokConstants.UNKNOWN_ERROR
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 * codingwithmitch.com
 */

open class Repository

suspend fun <T> Repository.safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ApiResult<T?> =
    withContext(dispatcher) {
        try {
            withTimeout(NETWORK_TIMEOUT) {
                delay(NETWORK_DELAY)
                Success(apiCall.invoke())
            }

        } catch (throwable: Throwable) {

            when (throwable) {
                is TimeoutCancellationException -> {
                    val code = 408 // timeout error
                    GenericError(code, NETWORK_ERROR_TIMEOUT)
                }

                is HttpException -> {
                    val code = throwable.code()
                    val errorReponse = convertErrorBody(throwable)
                    GenericError(code, errorReponse)
                }

                else -> {
                    GenericError(null, UNKNOWN_ERROR)
                }
            }
        }
    }

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.toString()
    } catch (exception: Exception) {
        UNKNOWN_ERROR
    }
}
















