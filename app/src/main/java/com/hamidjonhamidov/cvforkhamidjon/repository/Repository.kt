package com.hamidjonhamidov.cvforkhamidjon.repository

import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult
import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult.GenericError
import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult.Success
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_DELAY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_ERROR_TIMEOUT
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_ERROR_FAILURE
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_TIMEOUT
import kotlinx.coroutines.*
import retrofit2.HttpException

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 * codingwithmitch.com
 */

abstract class Repository {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T?
    ): ApiResult<T?> =
        withContext(dispatcher) {
            try {
                withTimeout(NETWORK_TIMEOUT) {
                    delay(NETWORK_DELAY)
                    Success(apiCall.invoke(), NETWORK_SUCCESS)
                }

            } catch (throwable: Throwable) {

                when (throwable) {
                    is TimeoutCancellationException -> {
                        val code = 408 // timeout error
                        GenericError(code, NETWORK_ERROR_TIMEOUT)
                    }

                    is HttpException -> {
                        val code = throwable.code()
                        GenericError(code, NETWORK_ERROR_FAILURE)
                    }

                    else -> {
                        GenericError(null, NETWORK_ERROR_FAILURE)
                    }
                }
            }
        }
}