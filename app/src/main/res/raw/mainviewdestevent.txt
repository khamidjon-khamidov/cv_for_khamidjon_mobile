package com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state


sealed class MainViewDestEvent {

    class HomeFragmentDest: MainViewDestEvent()

    class AboutMeFragmentDest : MainViewDestEvent()

    class MySkillsFragmentDest : MainViewDestEvent()

    class GetProjectsFragmentDest : MainViewDestEvent()

    class GetPostsFragmentDest: MainViewDestEvent()
}
















