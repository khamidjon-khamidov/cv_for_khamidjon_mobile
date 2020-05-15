package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent.MainActivityScope
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import com.hamidjonhamidov.cvforkhamidjon.repository.Repository
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState.*
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

@MainActivityScope
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
                        toFragment = stateEvent.toFragment,
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
                        toFragment = stateEvent.toFragment,
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
                        toFragment = stateEvent.toFragment,
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
                        toFragment = stateEvent.toFragment,
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
    ): Flow<DataState<MainViewState>> = flow {

        var response: ApiResult<List<SkillRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            ) // set it to network error no internet available default


        if (isNetworkAvailable)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getSkillsSync() } // if internet is available request from internet

        var cacheRepsonse: List<SkillModel>? =
            listOf() // set cache response default to empty list

        // if there has been some error from internet try to receive it from cache
        if (response is ApiResult.GenericError) {
            cacheRepsonse = withContext(Dispatchers.IO) { appDatabase.getSkillsDao().getSkills() }
        }

        val result = withContext(Dispatchers.Default) {
            object : ApiResponseHandler<MainViewState, SkillRemoteModel, SkillModel>(
                response,
                stateEvent,
                cacheRepsonse
            ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: StateEvent,
                    remoteResponse: List<SkillRemoteModel>
                ): DataState<MainViewState> {
                    val skillsList = remoteResponse.map { it.convertToSkillModel() }

                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getSkillsDao().insertManyAndReplace(skillsList)
                    }
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(skillsList)
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<SkillModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(cacheResponseObject)
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<SkillModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<SkillModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)

    }

    override fun getAchievements(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> = flow {
        var response: ApiResult<List<AchievementRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            ) // set it to network error no internet available default


        if (isNetworkAvailable)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getAchievementsSync() } // if internet is available request from internet

        var cacheRepsonse: List<AchievementModel>? =
            listOf() // set cache response default to empty list

        // if there has been some error from internet try to receive it from cache
        if (response is ApiResult.GenericError) {
            cacheRepsonse = withContext(Dispatchers.IO) {
                appDatabase.getAchievementsDao().getAllAchievements()
            }
        }

        val result = withContext(Dispatchers.Default) {
            object : ApiResponseHandler<MainViewState, AchievementRemoteModel, AchievementModel>(
                response,
                stateEvent,
                cacheRepsonse
            ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: StateEvent,
                    remoteResponse: List<AchievementRemoteModel>
                ): DataState<MainViewState> {
                    val achievementList = remoteResponse.map { it.convertToAchievmentModel() }

                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getAchievementsDao().insertManyAndReplace(achievementList)
                    }
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            achievementsFragmentView = AchievementsFragmentView(achievementList)
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<AchievementModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            achievementsFragmentView = AchievementsFragmentView(cacheResponseObject)
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<AchievementModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            achievementsFragmentView = AchievementsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<AchievementModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            achievementsFragmentView = AchievementsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)

    }

    override fun getProjects(
        stateEvent: StateEvent,
        isNetworkAvailable: Boolean
    ): Flow<DataState<MainViewState>> = flow{

        var response: ApiResult<List<ProjectsRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            ) // set it to network error no internet available default


        if (isNetworkAvailable)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getProjectsSync() } // if internet is available request from internet

        var cacheRepsonse: List<ProjectModel>? =
            listOf() // set cache response default to empty list

        // if there has been some error from internet try to receive it from cache
        if (response is ApiResult.GenericError) {
            cacheRepsonse =
                withContext(Dispatchers.IO) { appDatabase.getProjectsDao().getAllProjects() }
        }

        val result = withContext(Dispatchers.Default) {
            object : ApiResponseHandler<MainViewState, ProjectsRemoteModel, ProjectModel>(
                response,
                stateEvent,
                cacheRepsonse
            ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: StateEvent,
                    remoteResponse: List<ProjectsRemoteModel>
                ): DataState<MainViewState> {
                    val projectList = remoteResponse.map { it.convertToProjectModel() }

                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getProjectsDao().insertManyAndReplace(projectList)
                    }
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(projectList)
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<ProjectModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(cacheResponseObject)
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<ProjectModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: StateEvent,
                    cacheResponseObject: List<ProjectModel>
                ): DataState<MainViewState> {
                    return DataState(
                        toFragment = stateEvent.toFragment,
                        data = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)
    }

}