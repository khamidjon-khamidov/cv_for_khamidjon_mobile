package com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

private val TAG = "AppDebug"

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getCurrentViewStateOrNew(): MainViewState = viewState.value ?: MainViewState()

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getAboutMe(): AboutMeModel?{
    return getCurrentViewStateOrNew().homeFragmentView.aboutMe
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getSkills(): List<SkillModel>?{
    return getCurrentViewStateOrNew().mySkillsFragmentView.mySkills
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getProjects(): List<ProjectModel>?{
    return getCurrentViewStateOrNew().projectsFragmentView.projects
}

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun MainViewModel.getPosts(): List<PostModel>?{
    return getCurrentViewStateOrNew().postsFragmentView.posts
}
















