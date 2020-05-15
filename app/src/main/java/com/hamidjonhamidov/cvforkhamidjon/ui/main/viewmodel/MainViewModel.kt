package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamidjonhamidov.cvforkhamidjon.repository.main.MainRepository
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent.*
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.util.DataState
import com.hamidjonhamidov.cvforkhamidjon.util.Message
import com.hamidjonhamidov.cvforkhamidjon.util.NetworkConnection
import com.hamidjonhamidov.cvforkhamidjon.util.MyJob
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

//@OptIn(FlowPreview::class)
@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainViewModel
@Inject
constructor(
    private val mainRepository: MainRepository,
    private val networkConnection: NetworkConnection
) : BaseMainViewModel() {

    init {
        setUpChannel()
    }

    private fun setUpChannel() {
        dataChannel
            .asFlow()
            .onEach { dataState ->
                dataState.data?.let {
                    handleNewData(dataState.toFragment, it, dataState.message)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun offerToDataChannel(dataState: DataState<MainViewState>) {
        if (!dataChannel.isClosedForSend) {
            dataChannel.offer(dataState)
        }
    }

    fun setStateEvent(stateEvent: MainStateEvent) {
        val flowData: Flow<DataState<MainViewState>>
        val activeJob =
            when (stateEvent) {

                is GetHome -> {
                    flowData = mainRepository
                        .getAboutMe(stateEvent, networkConnection.isConectedToInternet())
                    MyJob.GetAboutMe()
                }

                is GetAboutMe -> {
                    flowData = mainRepository
                        .getAboutMe(stateEvent, networkConnection.isConectedToInternet())
                    MyJob.GetAchiements()
                }

                is GetMySkills -> {
                    flowData = mainRepository
                        .getMySkills(stateEvent, networkConnection.isConectedToInternet())
                    MyJob.GetMySkills()
                }

                is GetAchievements -> {
                    flowData = mainRepository
                        .getAchievements(stateEvent, networkConnection.isConectedToInternet())

                    MyJob.GetAchiements()
                }

                is GetProjects -> {
                    flowData = mainRepository
                        .getProjects(stateEvent, networkConnection.isConectedToInternet())

                    MyJob.GetProjects()
                }
            }

        launchJob(activeJob, flowData)
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

    private fun handleNewData(toFragment: String, data: MainViewState, message: Message) {
        setMessage(toFragment, message)

        setAboutMe(data.homeFragmentView.aboutMe)

        setMySkills(data.mySkillsFragmentView.mySkills)

        setAchievments(data.achievementsFragmentView.achievements)

        setProjects(data.projectsFragmentView.projects)
    }

}






























