package com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state


sealed class MainViewDestEvent {

    class HomeFragmentDest: MainViewDestEvent()

    class AboutMeFragmentDest : MainViewDestEvent()

    class MySkillsFragmentDest : MainViewDestEvent()

    class AchievementsFragmentDest : MainViewDestEvent()

    class GetProjectsFragmentDest : MainViewDestEvent()

    class GetPostsFragmentDest: MainViewDestEvent()
}
















