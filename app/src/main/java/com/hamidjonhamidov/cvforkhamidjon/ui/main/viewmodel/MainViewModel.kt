package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepository
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent.*
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.*
import com.hamidjonhamidov.cvforkhamidjon.util.constants.NetworkConstants.NETWORK_CACHE_SUCCESS_TITLE
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.RefreshLimitController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
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

    init {
        setUpChannel()
    }

    private fun setUpChannel() {
        dataChannel
            .asFlow()
            .onEach { dataState ->
                dataState.data?.let {
                    handleNewIncomingData(it, dataState.message)
                }

                handleNewMessage(dataState.toFragment, dataState.message)
            }
            .launchIn(viewModelScope)
    }

    private fun handleNewMessage(toFragment: String, message: Message) {
        setMessage(toFragment, FragmentMessage(message))
    }

    private fun offerToDataChannel(dataState: DataState<MainViewState>) {
        if (!dataChannel.isClosedForSend) {
            dataChannel.offer(dataState)
        }
    }

    fun setStateEvent(stateEvent: MainStateEvent) {
        val dataInFlow: Flow<DataState<MainViewState>>
        val activeJob =
            when (stateEvent) {

                is GetHome -> {
                    if (refreshLimitController.canHomeSync()) {
                        dataInFlow = mainRepository
                            .getAboutMe(stateEvent, networkConnection.isConectedToInternet())
                    }  else {
                        dataInFlow = mainRepository
                            .getAboutMe(stateEvent, false, false)
                        setMessage(stateEvent.toFragment, NOT_ALLOWED_MESSAGE)
                    }
                    MyJob.GetAboutMe()
                }

                is GetAboutMe -> {
                    if (refreshLimitController.canAboutMeSync()) {
                        dataInFlow = mainRepository
                            .getAboutMe(stateEvent, networkConnection.isConectedToInternet())
                    }  else {
                        dataInFlow = mainRepository
                            .getAboutMe(stateEvent, false, false)
                        setMessage(stateEvent.toFragment, NOT_ALLOWED_MESSAGE)
                    }

                    MyJob.GetAboutMe()
                }

                is GetMySkills -> {
                    if (refreshLimitController.canMySkillsSync()) {
                        dataInFlow = mainRepository
                            .getMySkills(stateEvent, networkConnection.isConectedToInternet())
                    }  else {
                        dataInFlow = mainRepository
                            .getMySkills(stateEvent, false, false)
                        setMessage(stateEvent.toFragment, NOT_ALLOWED_MESSAGE)
                    }
                    MyJob.GetMySkills()
                }

                is GetAchievements -> {
                    if (refreshLimitController.canAchievmentsSync()) {
                        dataInFlow = mainRepository
                            .getAchievements(stateEvent, networkConnection.isConectedToInternet())
                    }  else {
                        dataInFlow = mainRepository
                            .getAchievements(stateEvent, false, false)
                        setMessage(stateEvent.toFragment, NOT_ALLOWED_MESSAGE)
                    }

                    MyJob.GetAchiements()
                }

                is GetProjects -> {
                    if (refreshLimitController.canAchievmentsSync()) {
                        dataInFlow = mainRepository
                            .getAchievements(stateEvent, networkConnection.isConectedToInternet())
                    }  else {
                        dataInFlow = mainRepository
                            .getAchievements(stateEvent, false, false)
                        setMessage(stateEvent.toFragment, NOT_ALLOWED_MESSAGE)
                    }

                    MyJob.GetProjects()
                }
            }

        launchJob(activeJob, dataInFlow)
    }

    private fun launchJob(mJob: MyJob, jobFunction: Flow<DataState<MainViewState>>) {
        if (!isJobActive(mJob)) {
            addToJobs(mJob)
            jobFunction
                .onEach { dataState ->
                    offerToDataChannel(dataState)
                }
                .launchIn(viewModelScope)
        }
    }

    private fun handleNewIncomingData(data: MainViewState, message: Message) {

        data.homeFragmentView.aboutMe?.let {
            setAboutMe(it)
            if(message.title==NETWORK_CACHE_SUCCESS_TITLE){
                refreshLimitController.incrementAboutMeSyncTimes()
                refreshLimitController.incrementHomeSyncTimes()
            }
        }

        data.mySkillsFragmentView.mySkills?.let{
            setMySkills(it)
            if(message.title==NETWORK_CACHE_SUCCESS_TITLE){
                refreshLimitController.incrementMySkillsSyncTimes()
            }
        }

        data.achievementsFragmentView.achievements?.let{
            setAchievments(it)
            if(message.title==NETWORK_CACHE_SUCCESS_TITLE){
                refreshLimitController.incrementAchievmentsSyncTimes()
            }
        }

        data.projectsFragmentView.projects?.let {
            setProjects(it)
            if(message.title==NETWORK_CACHE_SUCCESS_TITLE){
                refreshLimitController.incrementProjectsSyncTime()
            }
        }
    }

    val NOT_ALLOWED_MESSAGE = FragmentMessage(Message(
        "Warning!!!",
        "As you daily limits has finished, data will be provided from Database",
        UIType.Dialog(),
        false
    ))
}






























