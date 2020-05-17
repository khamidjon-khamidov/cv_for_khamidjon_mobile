package com.hamidjonhamidov.cvforkhamidjon.ui.main.a_home

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.AboutMeModel
import com.hamidjonhamidov.cvforkhamidjon.ui.askPermission
import com.hamidjonhamidov.cvforkhamidjon.ui.delayInBackgLaunchInMain
import com.hamidjonhamidov.cvforkhamidjon.ui.main.BaseMainFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.getCurrentViewStateOrNew
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.getMessagesSize
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent.GetHome
import com.hamidjonhamidov.cvforkhamidjon.ui.showProgressBar
import com.hamidjonhamidov.cvforkhamidjon.ui.showToast
import com.hamidjonhamidov.cvforkhamidjon.util.StateEvent
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.FACEBOOK_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.GIT_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.LINKEDIN_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.TELEGRAM_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.WEBSITE_LINK
import com.hamidjonhamidov.cvforkhamidjon.util.constants.GeneralConstants.WRITE_TO_EXTERNAL_STORAGE_CODE
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class HomeFragment(
    viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: GlideManager,
    val homeStateEvent: StateEvent
) : BaseMainFragment<AboutMeModel>(R.layout.fragment_home, viewModelFactory, homeStateEvent) {

    private val TAG = "AppDebug"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableSocialButtons()
    }

    override fun initData() {
        if (viewModel.getCurrentViewStateOrNew().homeFragmentView.aboutMe == null) {
            activity?.showProgressBar(true)
            viewModel.setStateEvent(GetHome())
        } else {
            updateView(viewModel.getCurrentViewStateOrNew().homeFragmentView.aboutMe!!)
        }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        lifecycleScope.launchWhenCreated {
            while(true){
                delay(5000)
                Log.d(TAG, "${homeStateEvent.whichFragment} message size = ${viewModel.getMessagesSize(homeStateEvent.whichFragment)}")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_mi_refresh -> {
                Log.d(
                    TAG,
                    "HomeFragment: onOptionsItemSelected: ${viewModel.getMessagesSize(homeStateEvent.whichFragment)}"
                )

                viewModel.setStateEvent(GetHome())
                true
            }

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
            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun updateView(myModel: AboutMeModel) {
        activity?.showProgressBar(false)
        requestManager
            .setImage(myModel.pictureLink, home_iv_me)
        home_tv_des.text = myModel.description
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
        with(requireActivity()){
            showToast("Download Started!")
            delayInBackgLaunchInMain(lifecycleScope, 1000){
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

    fun enableSocialButtons(){
        home_ll_git.setOnClickListener {
            goToLink(GIT_LINK)
        }

        home_ll_linkedin.setOnClickListener {
            goToLink(LINKEDIN_LINK)
        }

        home_ll_facebook.setOnClickListener {
            goToLink(FACEBOOK_LINK)
        }

        home_ll_telegram.setOnClickListener {
            goToLink(TELEGRAM_LINK)
        }

        home_ll_mywebsite.setOnClickListener {
            goToLink(WEBSITE_LINK)
        }
    }

    fun goToLink(link: String){
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(myIntent)
    }
}


















