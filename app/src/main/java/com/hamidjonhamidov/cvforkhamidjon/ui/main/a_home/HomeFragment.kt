package com.hamidjonhamidov.cvforkhamidjon.ui.main.a_home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.ui.main.BaseMainFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainViewState
import com.hamidjonhamidov.cvforkhamidjon.ui.showProgressBar
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.RefreshLimitController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment
constructor(
    viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager
) : BaseMainFragment(R.layout.fragment_home, viewModelFactory) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObservers()
        initData()
    }

    override fun initData() {
//        TODO("Not yet implemented")
    }

    val observer: Observer<MainViewState> = Observer { viewState ->
        if (viewState != null) {
            viewState.aboutMeFragmentView.let {
                it.aboutMe?.let { aboutMe ->
                    updateView(aboutMe)
                }
            }
        }
    }

    override fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, observer)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_mi_download -> {
                // todo
                return true
            }

            R.id.home_mi_refresh -> {
                // todo
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

    fun updateView(aboutMe: AboutMeModel){
        activity?.showProgressBar(false)
        requestManager
            .setImage(aboutMe.pictureLink, home_iv_me)
        home_tv_des.text = aboutMe.description
    }
}
