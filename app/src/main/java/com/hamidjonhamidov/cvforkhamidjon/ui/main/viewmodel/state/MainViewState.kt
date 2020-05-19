package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state

import android.os.Parcelable
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.*
import com.hamidjonhamidov.cvforkhamidjon.util.DoublePostModel
import kotlinx.android.parcel.Parcelize

const val MAIN_VIEW_STATE_BUNDLE_KEY = "com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState"

@Parcelize
data class MainViewState(

    var homeFragmentView: HomeFragmentView = HomeFragmentView(),

    var aboutMeFragmentView: AboutMeFragmentView = AboutMeFragmentView(),

    var mySkillsFragmentView: MySkillsFragmentView = MySkillsFragmentView(),

    var achievementsFragmentView: AchievementsFragmentView = AchievementsFragmentView(),

    var projectsFragmentView: ProjectsFragmentView = ProjectsFragmentView(),

    var postsFragmentView: PostsFragmentView = PostsFragmentView()
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

    @Parcelize
    data class PostsFragmentView(
        var posts: List<PostModel>? = null,
        var doublePosts: List<DoublePostModel>? = null
    ): Parcelable

}













