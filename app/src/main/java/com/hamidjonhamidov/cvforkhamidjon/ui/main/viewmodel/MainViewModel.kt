package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepository
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewDestEvent.*
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.*
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_ALREADY_IN_PROGRESS
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_CACHE_SUCCESS_TITLE
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.MESSAGE_NOT_ALLOWED
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.RefreshLimitController
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    private val networkConnection: NetworkConnection,
    private val refreshLimitController: RefreshLimitController
) : BaseMainViewModel() {

    private val TAG = "AppDebug"

    init {
        setUpChannel()
    }

    private fun setUpChannel() {
        dataChannel
            .asFlow()
            .onEach { dataState ->
                // new data has come, so time to remove corresponding job
                jobManger.removeJob(dataState.stateEvent.responsibleJob)

                // if there is any data update view state
                dataState.viewState?.let {
                    handleNewIncomingData(dataState)
                }

                // process new message
                inboxManager.receiveNewMessage(
                    dataState.stateEvent.destinationView,
                    dataState.message
                )
            }
            .launchIn(viewModelScope)
    }


    private fun offerToDataChannel(dataState: DataState<MainViewState, MainStateEvent>) {
        if (!dataChannel.isClosedForSend) {
            dataChannel.offer(dataState)
        }
    }

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        val isNetworkAllowed = refreshLimitController.canSync(mainStateEvent.destinationView)
        val isNetworkAvailable = isNetworkAllowed and networkConnection.isConectedToInternet()

        // inbox as data is being processed set progress bar state to true
        inboxManager.setProgressBarStateAndNotify(mainStateEvent.destinationView, true)

        // if the job is active return
        if (jobManger.isJobActive(mainStateEvent.responsibleJob)) {
            inboxManager.receiveNewMessage(
                mainStateEvent.destinationView,
                MESSAGE_ALREADY_IN_PROGRESS
            )
            return
        }

        if (!isNetworkAllowed) {
            inboxManager.receiveNewMessage(
                mainStateEvent.destinationView,
                MESSAGE_NOT_ALLOWED
            )
        }

        val jobFunc: () -> Flow<DataState<MainViewState, MainStateEvent>> =
            when (mainStateEvent.destinationView) {

                is HomeFragmentDest -> {

                    {
                        mainRepository.getAboutMe(
                            mainStateEvent,
                            isNetworkAvailable,
                            isNetworkAllowed
                        )
                    }
                }

                is AboutMeFragmentDest -> {
                    {
                        mainRepository.getAboutMe(
                            mainStateEvent,
                            isNetworkAvailable,
                            isNetworkAllowed
                        )
                    }
                }

                is MySkillsFragmentDest -> {
                    {
                        mainRepository.getMySkills(
                            mainStateEvent,
                            isNetworkAvailable,
                            isNetworkAllowed
                        )
                    }

                }

                is GetProjectsFragmentDest -> {
                    {
                        mainRepository.getProjects(
                            mainStateEvent,
                            isNetworkAvailable,
                            isNetworkAllowed
                        )
                    }
                }

                is GetPostsFragmentDest -> {
                    {
                        mainRepository.getPosts(
                            mainStateEvent,
                            isNetworkAvailable,
                            isNetworkAllowed
                        )
                    }
                }
            }

        launchJob(mainStateEvent, jobFunc)
    }

    private fun launchJob(
        mainStateEvent: MainStateEvent,
        jobFunction: () -> Flow<DataState<MainViewState, MainStateEvent>>
    ) {
        jobManger.addJob(mainStateEvent.responsibleJob)

        jobFunction
            .invoke()
            .onEach { dataState ->
                offerToDataChannel(dataState)
            }
            .launchIn(viewModelScope)

    }

    private fun handleNewIncomingData(dataState: DataState<MainViewState, MainStateEvent>) {
        val stateEvent = dataState.stateEvent
        val data = dataState.viewState!!
        val message = dataState.message

        // if data is from network, increase sync time
        if (message.title == NETWORK_CACHE_SUCCESS_TITLE) {
            refreshLimitController.incrementSyncTime(stateEvent.destinationView)
        }

        dataState.viewState.homeFragmentView.aboutMe?.let {
            setAboutMe(it)
        }

        data.mySkillsFragmentView.mySkills?.let {
            setMySkills(it)
        }

        data.projectsFragmentView.projects?.let {
            setProjects(it)
        }

        data.postsFragmentView.posts?.let {
            setPosts(it)
        }
    }

}






























