package com.hamidjonhamidov.cvforkhamidjon.ui.main.b_aboutme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import com.hamidjonhamidov.cvforkhamidjon.ui.copyToClipboard
import com.hamidjonhamidov.cvforkhamidjon.ui.goToLink
import com.hamidjonhamidov.cvforkhamidjon.ui.main.BaseMainFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.getCurrentViewStateOrNew
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.showToast
import com.hamidjonhamidov.cvforkhamidjon.util.DateUtil
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import kotlinx.android.synthetic.main.fragment_about_me.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi


@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
class AboutMeFragment(
    viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager,
    val aboutMeStateEvent: MainStateEvent
) : BaseMainFragment<AboutMeModel>(
    R.layout.fragment_about_me,
    R.menu.only_refresh_menu,
    viewModelFactory,
    aboutMeStateEvent
) {


    override fun subscribeDataObservers() {
        // observe data in about me
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.aboutMeFragmentView?.let {
                it.aboutMe?.let { aboutMe ->
                    updateView(aboutMe)
                }
            }
        })
    }

    override fun initData() {
        if (viewModel.getCurrentViewStateOrNew().homeFragmentView.aboutMe == null &&
            !viewModel.jobManger.isJobActive(aboutMeStateEvent.responsibleJob)
        ) {
            viewModel.setStateEvent(aboutMeStateEvent)
        } else {
            updateView(viewModel.getCurrentViewStateOrNew().homeFragmentView.aboutMe)
        }

        aboutme_tv_experience.text = DateUtil.getDifferenceWithCurrentDate()

        aboutme_tv_lsbu.setOnClickListener {
            activity?.showToast("Sorry, data have not been received yet!")
        }

        aboutme_tv_tuit.setOnClickListener {
            activity?.showToast("Sorry, data have not been received yet!")
        }

        aboutme_tv_phone.setOnClickListener {
            activity?.copyToClipboard("+44 075653 36207")
            activity?.showToast("Copied to Clipboard")
        }

        aboutme_tv_email.setOnClickListener {
            activity?.copyToClipboard("hamidovhamid1998@gmail.com")
            activity?.showToast("Copied to Clipboard")
        }
    }

    override fun updateView(myModel: AboutMeModel?) {

        myModel?.education?.get(0)?.link?.let { edu ->
            aboutme_tv_lsbu.setOnClickListener {
                activity?.goToLink(edu)
            }
        }


        myModel?.education?.get(1)?.link?.let { edu ->
            aboutme_tv_tuit.setOnClickListener {
                activity?.goToLink(edu)
            }
        }

        myModel?.phone?.let { phone->
            aboutme_tv_phone.setOnClickListener {
                activity?.copyToClipboard(phone)
                activity?.showToast("Copied to Clipboard")
            }
        }

        myModel?.email?.let { email->
            aboutme_tv_email.setOnClickListener {
                activity?.copyToClipboard(email)
                activity?.showToast("Copied to Clipboard")
            }
        }
    }

    override fun updateView(modelList: List<AboutMeModel>) {

    }
}
