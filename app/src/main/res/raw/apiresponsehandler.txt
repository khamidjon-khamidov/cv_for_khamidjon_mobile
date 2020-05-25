package com.hamidjonhamidov.cvforkhamidjon.util

import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_EMPTY
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_ERROR_FAILURE
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_ERROR_NO_INTERNET
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_ERROR_TIMEOUT

abstract class ApiResponseHandler<ViewState, StateEvent, RemoteResponseObject, CacheResponseObject>(
    val remoteResponseObject: ApiResult<List<RemoteResponseObject>?>,
    val stateEvent: StateEvent,
    val cacheResponseObject: List<CacheResponseObject>?,
    val isNetworkAllowed: Boolean
) {

    val result: DataState<ViewState, StateEvent> = processResponse()


    fun processResponse(): DataState<ViewState, StateEvent> {
        if(!isNetworkAllowed){
            return if(cacheResponseObject?.isNotEmpty()==true){
                handleNetworkNotAllowedCacheSuccess(stateEvent, cacheResponseObject)
            } else {
                DataState(
                    stateEvent = stateEvent,
                    message = MESSAGE_NETWORK_NOT_ALLOWED_CACHE_EMPTY
                )
            }
        }

        return when (remoteResponseObject) {
            is ApiResult.GenericError -> {

                when (remoteResponseObject.errorMessage) {

                    NETWORK_ERROR_FAILURE -> {
                        if (cacheResponseObject?.isNotEmpty() == true) {
                            handleNetworkFailureCacheSuccess(stateEvent, cacheResponseObject)
                        } else {
                            DataState(
                                stateEvent = stateEvent,
                                message = MESSAGE_NETWORK_ERROR_CACHE_EMPTY.copy()
                            )
                        }
                    }

                    NETWORK_ERROR_NO_INTERNET -> {
                        if (cacheResponseObject?.isNotEmpty() == true) {
                            handleNoInternetCacheSuccess(stateEvent, cacheResponseObject)
                        } else {
                            DataState(
                                stateEvent = stateEvent,
                                message = MESSAGE_NO_INTERNET_CACHE_EMPTY.copy()
                            )
                        }
                    }

                    NETWORK_ERROR_TIMEOUT -> {
                        if (cacheResponseObject?.isNotEmpty() == true) {
                            handleNetworkTimeoutCacheSuccess(stateEvent, cacheResponseObject)
                        } else {
                            DataState(
                                stateEvent = stateEvent,
                                message = MESSAGE_NETWORK_TIMEOUT_CACHE_EMPTY
                            )
                        }
                    }

                    else -> DataState(
                        stateEvent = stateEvent,
                        message = MESSAGE_NETWORK_ERROR_CACHE_EMPTY
                    )
                }
            }

            is ApiResult.Success -> {
                handleNetworkSuccessCacheSuccess(stateEvent, remoteResponseObject.value!!)
            }
        }
    }

    abstract fun handleNetworkNotAllowedCacheSuccess(
        stateEvent: StateEvent,
        cacheResponseObject: List<CacheResponseObject>
    ): DataState<ViewState, StateEvent>


    abstract fun handleNetworkSuccessCacheSuccess(
        stateEvent: StateEvent,
        remoteResponse: List<RemoteResponseObject>
    ): DataState<ViewState, StateEvent>

    abstract fun handleNetworkTimeoutCacheSuccess(
        stateEvent: StateEvent,
        cacheResponseObject: List<CacheResponseObject>
    ): DataState<ViewState, StateEvent>

    abstract fun handleNoInternetCacheSuccess(
        stateEvent: StateEvent,
        cacheResponseObject: List<CacheResponseObject>
    ): DataState<ViewState, StateEvent>

    abstract fun handleNetworkFailureCacheSuccess(
        stateEvent: StateEvent,
        cacheResponseObject: List<CacheResponseObject>
    ): DataState<ViewState, StateEvent>
}
