package com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.state

import android.os.Parcelable
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.AchievementModel
import kotlinx.android.parcel.Parcelize

const val achievments_view_state_bundle_key = "com.hamidjonhamidov.cvforkhamidjon.ui.achievments.viewmodel.state.AchievmentsViewState"

@Parcelize
data class AchievementsViewState(
    val achievementsFragmentView: AchievementsFragmentView = AchievementsFragmentView()
): Parcelable {

    @Parcelize
    data class AchievementsFragmentView(
        var achievements: List<AchievementModel>? = null
    ): Parcelable
}