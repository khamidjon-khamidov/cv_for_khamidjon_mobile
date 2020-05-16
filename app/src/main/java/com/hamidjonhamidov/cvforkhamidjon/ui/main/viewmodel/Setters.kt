package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setAboutMe(aboutMe: AboutMeModel){
    val update = getCurrentViewStateOrNew()
    update.homeFragmentView.aboutMe = aboutMe
    update.aboutMeFragmentView.aboutMe = aboutMe
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setMySkills(skills: List<SkillModel>){
    val update = getCurrentViewStateOrNew()
    update.mySkillsFragmentView.mySkills = skills
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setAchievments(achievments: List<AchievementModel>){
    val update = getCurrentViewStateOrNew()
    update.achievementsFragmentView.achievements = achievments
    setViewState(update)
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setProjects(projects: List<ProjectModel>){
    val update = getCurrentViewStateOrNew()
    update.projectsFragmentView.projects = projects
    setViewState(update)
}


@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setMessage(toFragment: String, message: FragmentMessage) {
    if(messages[toFragment]==null){
        messages[toFragment] = LinkedList(listOf(message))
    } else {
        messages[toFragment]!!.add(message)
    }

    notifyFragmentsWithNewMessage()

}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.removeLastMessage(toFragment: String) {
    messages[toFragment]?.remove()
}

@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
fun MainViewModel.notifyFragmentsWithNewMessage() {
    listenMessageLiveData.value = !(listenMessageLiveData.value ?: false)
}
























