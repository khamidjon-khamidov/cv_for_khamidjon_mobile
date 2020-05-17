package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel

import android.util.Log
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*

private val TAG = "AppDebug"

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
fun MainViewModel.setMessage(whichFragment: String, message: FragmentMessage) {
    if(messages[whichFragment]==null){
        Log.d(TAG, ": setMessage: messages created: ${message}")
        messages[whichFragment] = LinkedList(listOf(message))
    } else {
        Log.d(TAG, ": setMessage: add to old messages created: ${message}")
        messages[whichFragment]!!.add(message)
    }

    notifyFragmentsWithNewMessage()

}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.setLastMessageToProgress(toFragment: String){
    messages[toFragment]?.peek()?.progressStatus = FragmentMessage.MESSAGE_IN_PROGRESS
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.removeLastMessage(toFragment: String) {
    try{
        messages[toFragment]?.poll()
        Log.d(TAG, ": removeLastMessage: messagessize = ${getMessagesSize(toFragment)}")
    } catch (e: Exception){
        Log.d(TAG, ": removeLastMessage: last message couldn't be removed ")
    }

}

























