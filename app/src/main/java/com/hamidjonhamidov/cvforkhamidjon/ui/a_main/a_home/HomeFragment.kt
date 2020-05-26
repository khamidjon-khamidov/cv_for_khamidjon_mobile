package com.hamidjonhamidov.cvforkhamidjon.ui.a_main.a_home

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.ui.*
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.BaseMainFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.getAboutMe
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.getCurrentViewStateOrNew
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.FACEBOOK_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.GIT_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.LINKEDIN_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.TELEGRAM_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.WEBSITE_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.WRITE_TO_EXTERNAL_STORAGE_CODE
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment(
    viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager,
    val homeStateEvent: MainStateEvent
) : BaseMainFragment<AboutMeModel>(R.layout.fragment_home, R.menu.home_menu, viewModelFactory, homeStateEvent) {

    private val TAG = "AppDebug"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableSocialButtons()
    }

    override fun initData() {
        if (viewModel.getAboutMe() == null &&
            !viewModel.jobManger.isJobActive(homeStateEvent.responsibleJob)
        ) {
            viewModel.setStateEvent(homeStateEvent)
        }

        updateView(viewModel.getCurrentViewStateOrNew().homeFragmentView.aboutMe)
    }


    override fun subscribeDataObservers() {
        // observe data
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.aboutMeFragmentView?.let {
                it.aboutMe?.let { aboutMe ->
                    updateView(aboutMe)
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.home_mi_download -> {
                viewModel.viewState.value?.aboutMeFragmentView?.aboutMe?.cvLink?.let {
                    if (requireActivity().askPermission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            WRITE_TO_EXTERNAL_STORAGE_CODE
                        )
                    ) {
                        requestDownload(it)
                    }
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun updateView(myModel: AboutMeModel?) {
        myModel?.let {
            requestManager
                .setImage(it.pictureLink, home_iv_me)
            home_tv_des.text = it.description
        }
    }

    fun requestDownload(uRl: String) {

        val mgr = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val downloadUri = Uri.parse(uRl);
        val request = DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
        )
            .setAllowedOverRoaming(false)
            .setTitle("Khamidjon_Khamidov.docx")
            .setDescription("CV for Khamidjon Khamidov")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "Khamidjon_Khamidov.docx"
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        mgr.enqueue(request)
        with(requireActivity()) {
            showToast("Download Started!")
            delayInBackgLaunchInMain(lifecycleScope, 1000) {
                activity?.showToast("path: .../${Environment.DIRECTORY_DOWNLOADS}/Khamidjon_Khamidov.docx")
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            WRITE_TO_EXTERNAL_STORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.viewState.value?.aboutMeFragmentView?.aboutMe?.cvLink?.let {
                        requestDownload(it)
                    }
                } else {
                    activity?.showToast("Please accept permission to download my CV!")
                }
            }
        }
    }

    fun enableSocialButtons() {
        home_ll_git.setOnClickListener {
            activity?.goToLink(GIT_LINK)
        }

        home_ll_linkedin.setOnClickListener {
            activity?.goToLink(LINKEDIN_LINK)
        }

        home_ll_facebook.setOnClickListener {
            activity?.goToLink(FACEBOOK_LINK)
        }

        home_ll_telegram.setOnClickListener {
            activity?.goToLink(TELEGRAM_LINK)
        }

        home_ll_mywebsite.setOnClickListener {
            activity?.goToLink(WEBSITE_LINK)
        }
    }

    override fun updateView(modelList: List<AboutMeModel>?) {}

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "HomeFragment: onDestroy: ")
    }
}


















