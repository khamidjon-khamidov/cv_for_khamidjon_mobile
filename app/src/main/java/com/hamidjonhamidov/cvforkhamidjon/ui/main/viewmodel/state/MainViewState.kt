package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state

import android.os.Parcelable
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import com.hamidjonhamidov.cvforkhamidjon.util.MyJob
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

const val MAIN_VIEW_STATE_BUNDLE_KEY = "com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState"

@Parcelize
data class MainViewState(
    var activeJobs: @RawValue HashSet<MyJob> = HashSet(),

    var homeFragmentView: HomeFragmentView = HomeFragmentView(),

    var aboutMeFragmentView: AboutMeFragmentView = AboutMeFragmentView(),

    var mySkillsFragmentView: MySkillsFragmentView = MySkillsFragmentView(),

    var achievementsFragmentView: AchievementsFragmentView = AchievementsFragmentView(),

    var projectsFragmentView: ProjectsFragmentView = ProjectsFragmentView()

) : Parcelable {

    @Parcelize
    data class HomeFragmentView(
        var aboutMe: AboutMeModel? = null
    ): Parcelable

    @Parcelize
    data class AboutMeFragmentView(
        var aboutMe: AboutMeModel? = null
    ): Parcelable

    @Parcelize
    data class MySkillsFragmentView(
        var mySkills: List<SkillModel>? = null
    ): Parcelable

    @Parcelize
    data class AchievementsFragmentView(
        var achievements: List<AchievementModel>? = null
    ): Parcelable

    @Parcelize
    data class ProjectsFragmentView(
        var projects: List<ProjectModel>? = null
    ): Parcelable

}













