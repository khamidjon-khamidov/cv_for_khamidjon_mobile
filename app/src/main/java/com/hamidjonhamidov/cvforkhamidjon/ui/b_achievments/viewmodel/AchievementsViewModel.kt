package com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel

import androidx.lifecycle.viewModelScope
import com.hamidjonhamidov.cvforkhamidjon.repository.achievments.AchievementsRepository
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsViewDestEvent.AchievementsFragmentDest
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state.AchievementsViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.NetworkConnection
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.RefreshLimitController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@FlowPreview
class AchievementsViewModel
@Inject
constructor(
    private val achievmentsRepository: AchievementsRepository,
    private val networkConnection: NetworkConnection,
    private val refreshLimitController: RefreshLimitController
) : BaseAchievmentsViewModel() {

    private val TAG = "AppDebug"

    init {
        setUpChannel()
    }


    private fun setUpChannel(){
        dataChannel
            .asFlow()
            .onEach { dataState ->
                // new data has come, so time to remove corresponding job
                jobManger.removeJob(dataState.stateEvent.responsibleJob)

                // if there is any data update view state
                dataState.viewState?.let {
                    handleNewIncomingData(dataState)
                }

                // process the new message
                inboxManager.receiveNewMessage(
                    dataState.stateEvent.destinationView,
                    dataState.message
                )
            }
            .launchIn(viewModelScope)
    }

    private fun offerToDataChannel(dataState: DataState<AchievementsViewState, AchievementsStateEvent>) {
        if(!dataChannel.isClosedForSend){
            dataChannel.offer(dataState)
        }
    }

    fun setStateEvent(achievmentsStateEvent: AchievementsStateEvent){
        val isNetworkAllowed = refreshLimitController.canSync(achievmentsStateEvent.destinationView)
        val isNetworkAvailable = isNetworkAllowed and networkConnection.isConectedToInternet()

        // inbox as data is being processed set progress bar state to true
        inboxManager.setProgressBarStateAndNotify(achievmentsStateEvent.destinationView, true)

        // if the job is active return
        if(jobManger.isJobActive(achievmentsStateEvent.responsibleJob)){
            inboxManager.receiveNewMessage(
                achievmentsStateEvent.destinationView,
                NetworkConstants.MESSAGE_ALREADY_IN_PROGRESS
            )
            return
        }

        if(!isNetworkAllowed){
            inboxManager.receiveNewMessage(
                achievmentsStateEvent.destinationView,
                NetworkConstants.MESSAGE_NOT_ALLOWED
            )
        }

        val jobFunc: () -> Flow<DataState<AchievementsViewState, AchievementsStateEvent>> =
            when(achievmentsStateEvent.destinationView){

                is AchievementsFragmentDest -> {
                    {
                        achievmentsRepository.getAchievements(
                            achievmentsStateEvent,
                            isNetworkAvailable,
                            isNetworkAllowed
                        )
                    }
                }
            }

        launchJob(achievmentsStateEvent, jobFunc)
    }

    private fun launchJob(
        achievmentsStateEvent: AchievementsStateEvent,
        jobFunction: () -> Flow<DataState<AchievementsViewState, AchievementsStateEvent>>
    ){
        jobManger.addJob(achievmentsStateEvent.responsibleJob)

        jobFunction
            .invoke()
            .onEach {  dataState ->
                offerToDataChannel(dataState)
            }
            .launchIn(viewModelScope)
    }

    private fun handleNewIncomingData(dataState: DataState<AchievementsViewState, AchievementsStateEvent>){
        val stateEvent = dataState.stateEvent
        val data = dataState.viewState!!
        val message = dataState.message

        if(message.title == NetworkConstants.NETWORK_CACHE_SUCCESS_TITLE){
            refreshLimitController.incrementSyncTime(stateEvent.destinationView)
        }

        dataState.viewState.achievementsFragmentView.achievements?.let {
            setAchievments(it)
        }
    }
}


















