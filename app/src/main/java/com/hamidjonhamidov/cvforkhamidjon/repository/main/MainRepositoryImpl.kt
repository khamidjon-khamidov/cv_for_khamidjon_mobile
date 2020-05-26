package com.hamidjonhamidov.cvforkhamidjon.repository.main

import com.hamidjonhamidov.cvforkhamidjon.data_requests.api.main.MainApiService
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.di.main_subcomponent.MainActivityScope
import com.hamidjonhamidov.cvforkhamidjon.models.api.main.*
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.*
import com.hamidjonhamidov.cvforkhamidjon.repository.NetworkApiCall
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainViewState.*
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

@MainActivityScope
class MainRepositoryImpl
@Inject
constructor(
    val apiService: MainApiService,
    val appDatabase: AppDatabase
) : MainRepository, NetworkApiCall() {

    private val TAG = "AppDebug"

    override fun getAboutMe(
        stateEvent: MainStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean
    ): Flow<DataState<MainViewState, MainStateEvent>> = flow {

        delay(PROCESS_DELAY)
        // set network response to network error no internet available as a default
        var response: ApiResult<List<AboutMeRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            )

        // if network is available and allowed, try to get from remote
        if (isNetworkAvailable && isNetworkAllowed)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getAboutMeSync() }

        // set cache response to default empty
        var cacheRepsonse: List<AboutMeModel>? =
            listOf()

        // if there has been some error from internet or internet not allowed, try to receive it from cache
        if (response is ApiResult.GenericError || !isNetworkAllowed) {
            cacheRepsonse = withContext(Dispatchers.IO) { appDatabase.getAboutMeDao().getAboutMe() }
        }

        // process cache and remote response
        val result = withContext(Dispatchers.Default) {
            object :
                ApiResponseHandler<MainViewState, MainStateEvent, AboutMeRemoteModel, AboutMeModel>(
                    response,
                    stateEvent,
                    cacheRepsonse,
                    isNetworkAllowed
                ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: MainStateEvent,
                    remoteResponse: List<AboutMeRemoteModel>
                ): DataState<MainViewState, MainStateEvent> {
                    val aboutMeModel = remoteResponse[0].convertToAboutMeModel()
                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getAboutMeDao().replaceAboutMe(aboutMeModel)
                    }
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            homeFragmentView = HomeFragmentView(aboutMeModel)
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<AboutMeModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            homeFragmentView = HomeFragmentView(cacheResponseObject[0])
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<AboutMeModel>
                ): DataState<MainViewState, MainStateEvent> {

                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            homeFragmentView = HomeFragmentView(cacheResponseObject[0])
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<AboutMeModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            homeFragmentView = HomeFragmentView(cacheResponseObject[0])
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkNotAllowedCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<AboutMeModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            homeFragmentView = HomeFragmentView(cacheResponseObject[0])
                        ),
                        message = MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)

    }

    override fun getMySkills(
        stateEvent: MainStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean
    ): Flow<DataState<MainViewState, MainStateEvent>> = flow {

        delay(PROCESS_DELAY)

        // set remote response to network error no internet available as default
        var response: ApiResult<List<SkillRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            )

        // if internet is available request from internet
        if (isNetworkAvailable)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getSkillsSync() }

        // set cache response to empty list as default
        var cacheRepsonse: List<SkillModel>? =
            listOf()

        // if there has been some error from internet try to receive it from cache
        if (response is ApiResult.GenericError) {
            cacheRepsonse = withContext(Dispatchers.IO) { appDatabase.getSkillsDao().getSkills() }
        }

        val result = withContext(Dispatchers.Default) {
            object :
                ApiResponseHandler<MainViewState, MainStateEvent, SkillRemoteModel, SkillModel>(
                    response,
                    stateEvent,
                    cacheRepsonse,
                    isNetworkAllowed
                ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: MainStateEvent,
                    remoteResponse: List<SkillRemoteModel>
                ): DataState<MainViewState, MainStateEvent> {
                    val skillsList = remoteResponse.map { it.convertToSkillModel() }

                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getSkillsDao().insertManyAndReplace(skillsList)
                    }
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(skillsList)
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<SkillModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(cacheResponseObject)
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<SkillModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<SkillModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkNotAllowedCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<SkillModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            mySkillsFragmentView = MySkillsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)

    }

    override fun getProjects(
        stateEvent: MainStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean
    ): Flow<DataState<MainViewState, MainStateEvent>> = flow {

        delay(PROCESS_DELAY)

        // set remote response to network error no internet available as default
        var response: ApiResult<List<ProjectRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            )

        // if internet is available, request from internet
        if (isNetworkAvailable)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getProjectsSync() }

        // set cache response to empty list as default
        var cacheRepsonse: List<ProjectModel>? =
            listOf()

        // if there has been some error from internet try to receive it from cache
        if (response is ApiResult.GenericError) {
            cacheRepsonse =
                withContext(Dispatchers.IO) { appDatabase.getProjectsDao().getAllProjects() }
        }

        val result = withContext(Dispatchers.Default) {
            object :
                ApiResponseHandler<MainViewState, MainStateEvent, ProjectRemoteModel, ProjectModel>(
                    response,
                    stateEvent,
                    cacheRepsonse,
                    isNetworkAllowed
                ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: MainStateEvent,
                    remoteResponse: List<ProjectRemoteModel>
                ): DataState<MainViewState, MainStateEvent> {
                    val projectList = remoteResponse.map { it.convertToProjectModel() }

                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getProjectsDao().insertManyAndReplace(projectList)
                    }
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(projectList)
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<ProjectModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(cacheResponseObject)
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<ProjectModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<ProjectModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkNotAllowedCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<ProjectModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            projectsFragmentView = ProjectsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)
    }

    override fun getPosts(
        stateEvent: MainStateEvent,
        isNetworkAvailable: Boolean,
        isNetworkAllowed: Boolean
    ): Flow<DataState<MainViewState, MainStateEvent>> = flow {

        delay(PROCESS_DELAY)

        // set remote response to network error no internet available as default
        var response: ApiResult<List<PostRemoteModel>?> =
            ApiResult.GenericError(
                null,
                NetworkConstants.NETWORK_ERROR_NO_INTERNET
            )

        // if internet is available, request from internet
        if (isNetworkAvailable)
            response =
                safeApiCall(Dispatchers.IO) { apiService.getPostsSync() }

        // set cache response to empty list as default
        var cacheRepsonse: List<PostModel>? =
            listOf()

        // if there has been some error from internet try to receive it from cache
        if (response is ApiResult.GenericError) {
            cacheRepsonse =
                withContext(Dispatchers.IO) { appDatabase.getPostsDao().getAllPosts() }
        }

        val result = withContext(Dispatchers.Default) {
            object :
                ApiResponseHandler<MainViewState, MainStateEvent, PostRemoteModel, PostModel>(
                    response,
                    stateEvent,
                    cacheRepsonse,
                    isNetworkAllowed
                ) {
                override fun handleNetworkSuccessCacheSuccess(
                    stateEvent: MainStateEvent,
                    remoteResponse: List<PostRemoteModel>
                ): DataState<MainViewState, MainStateEvent> {
                    val postList = remoteResponse.map { it.convertToPostModel() }

                    GlobalScope.launch((Dispatchers.IO)) {
                        appDatabase.getPostsDao().insertManyAndReplace(postList)
                    }
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            postsFragmentView = PostsFragmentView(postList)
                        ),
                        message = MESSAGE_NETWORK_SUCCESS_CACHE_SUCCESSS.copy()
                    )
                }

                override fun handleNetworkTimeoutCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<PostModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            postsFragmentView = PostsFragmentView(cacheResponseObject)
                        ),
                        message = MESSSAGE_NETWORK_TIMEOUT_CACHE_SUCCESS
                    )
                }

                override fun handleNoInternetCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<PostModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            postsFragmentView = PostsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NO_INTERNET_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkFailureCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<PostModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            postsFragmentView = PostsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_ERROR_CACHE_SUCCESS
                    )
                }

                override fun handleNetworkNotAllowedCacheSuccess(
                    stateEvent: MainStateEvent,
                    cacheResponseObject: List<PostModel>
                ): DataState<MainViewState, MainStateEvent> {
                    return DataState(
                        stateEvent = stateEvent,
                        viewState = MainViewState(
                            postsFragmentView = PostsFragmentView(cacheResponseObject)
                        ),
                        message = MESSAGE_NETWORK_NOT_ALLOWED_CACHE_SUCCESS
                    )
                }
            }.result
        }

        emit(result)
    }
}























