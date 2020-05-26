package com.hamidjonhamidov.cvforkhamidjon.repository.achievments

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.achievments.AchievmentsApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.di.achievements_subcomponent.AchievmentsScope
import com.hamidjonhamidov.cvforkhamidjon.models.api.achievments.AchievementRemoteModel
import com.hamidjonhamidov.cvforkhamidjon.models.api.achievments.convertToAchievmentModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.repository.NetworkApiCall
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsViewState
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsViewState.AchievementsFragmentView
import com.hamidjonhamidov.cvforkhamidjon.util.ApiResponseHandler
import com.hamidjonhamidov.cvforkhamidjon.util.ApiResult
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NO_INTERNET_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.PROCESS_DELAY
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@AchievmentsScope
class AchievementsRepositoryImpl
@Inject
constructor(
    val apiService: AchievmentsApiService,
    val appDatabase: AppDatabase
) : AchievementsRepository, NetworkApiCall() {


    override fun getAchievements(
        stateEvent: AchievementsStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean
    ): Flow<DataState<AchievementsViewState, AchievementsStateEvent>> = flow {

        delay(PROCESS_DELAY)

        // set remote response to network error no internet available as default
        var response: ApiResult<List<AchievementRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            )


        // if internet is available, request from internet
        if (isNetworkAvailable)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getAchievementsSync() }


        // set cache response to empty list as default
        var cacheRepsonse: List<AchievementModel>? =
            listOf()

        // if there has been some error from internet try to receive it from cache
        if (response is ApiResult.GenericError) {
            cacheRepsonse = withContext(Dispatchers.IO) {
                appDatabase.getAchievementsDao().getAllAchievements()
            }
        }

        val result = withContext(Dispatchers.Default) {
            object :
                ApiResponseHandler<AchievementsViewState, AchievementsStateEvent, AchievementRemoteModel, AchievementModel>(
                    response,
                    stateEvent,
                    cacheRepsonse,
                    isNetworkAllowed
                ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: AchievementsStateEvent,
                    remoteResponse: List<AchievementRemoteModel>
                ):  DataState<AchievementsViewState, AchievementsStateEvent> {
                    val achievementList = remoteResponse.map { it.convertToAchievmentModel() }

                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getAchievementsDao().insertManyAndReplace(achievementList)
                    }
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = AchievementsViewState(
                            achievementsFragmentView = AchievementsFragmentView(
                                achievementList
                            )
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: AchievementsStateEvent,
                    cacheResponseObject: List<AchievementModel>
                ): DataState<AchievementsViewState, AchievementsStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = AchievementsViewState(
                            achievementsFragmentView = AchievementsFragmentView(cacheResponseObject)
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: AchievementsStateEvent,
                    cacheResponseObject: List<AchievementModel>
                ): DataState<AchievementsViewState, AchievementsStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = AchievementsViewState(
                            achievementsFragmentView = AchievementsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: AchievementsStateEvent,
                    cacheResponseObject: List<AchievementModel>
                ): DataState<AchievementsViewState, AchievementsStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = AchievementsViewState(
                            achievementsFragmentView = AchievementsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkNotAllowedCacheSuccess(
                    stateEvent: AchievementsStateEvent,
                    cacheResponseObject: List<AchievementModel>
                ): DataState<AchievementsViewState, AchievementsStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = AchievementsViewState(
                            achievementsFragmentView = AchievementsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)

    }

}


















