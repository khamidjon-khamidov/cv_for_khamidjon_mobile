package com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel

import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.AchievementModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi


@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
fun AchievementsViewModel.setAchievments(achievments: List<AchievementModel>){
    val update = getCurrentViewStateOrNew()
    update.achievementsFragmentView.achievements = achievments
    setViewState(update)
}








