package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.di.main.MainScope
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.AboutMeRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.convertToAboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.repository.Repository
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState.HomeFragmentView
import com.hamidjonhamidov.cvforkhamidjon.util.ApiResponseHandler
import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.StateEvent
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@MainScope
class MainRepositoryImpl
@Inject
constructor(
    val apiService: MainApiService,
    val appDatabase: AppDatabase
) : MainRepository, Repository() {

    override fun getAboutMe(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> = flow {

        var response: ApiResult<List<AboutMeRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            ) // set it to network error no internet available default


        if (isNetworkAvailable)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getAboutMeSync() } // if internet is available request from internet

        var cacheRepsonse: List<AboutMeModel>? =
            listOf() // set cache response default to empty list

        // if there has been some error from internet try to receive it from cache
        if (response is ApiResult.GenericError) {
            cacheRepsonse = withContext(Dispatchers.IO) { appDatabase.getAboutMeDao().getAboutMe() }
        }

        val result = withContext(Dispatchers.Default) {
            object : ApiResponseHandler<MainViewState, AboutMeRemoteModel, AboutMeModel>(
                response,
                stateEvent,
                cacheRepsonse
            ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: StateEvent,
                    remoteResponse: List<AboutMeRemoteModel>
                ): DataState<MainViewState> {
                    val aboutMeModel = remoteResponse[0].convertToAboutMeModel()
                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getAboutMeDao().replaceAboutMe(aboutMeModel)
                    }
                    return DataState(
                        toFragment = stateEvent.toString(),
                        data = MainViewState(
                            homeFragmentView = HomeFragmentView(aboutMeModel)
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<AboutMeModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toString(),
                        data = MainViewState(
                            homeFragmentView = HomeFragmentView(cacheResponseObject[0])
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<AboutMeModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toString(),
                        data = MainViewState(
                            homeFragmentView = HomeFragmentView(cacheResponseObject[0])
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<AboutMeModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toString(),
                        data = MainViewState(
                            homeFragmentView = HomeFragmentView(cacheResponseObject[0])
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)

    }

    override fun getMySkills(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> {
        TODO("Not yet implemented")
    }

    override fun getAchievements(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> {
        TODO("Not yet implemented")
    }

    override fun getProjects(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> {
        TODO("Not yet implemented")
    }

}