package com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail.DetailsFragment.Companion.RES_NAME_ANDROID
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail.DetailsFragment.Companion.RES_NAME_OTHERS
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.AchievementsViewModel
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.getAlgorithms
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.getAndroid
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.getOthers
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DetailsFragment(
    val glideManager: GlideManager,
    viewModelFactory: ViewModelProvider.Factory
) : Fragment(R.layout.fragment_details) {

    lateinit var link: String
    lateinit var des: String
    lateinit var title: String

    val viewModel: AchievementsViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { processBundle(it) }
    }

    private fun bindViews(){
        glideManager.setImage(link, detail_iv_image)
        detail_tv_title.text = title
        detail_tv_des.text = des
    }

    private val TAG = "AppDebug"
    private fun processBundle(bundle: Bundle){
        val name = bundle.getString("resName") ?: return
        val pos = bundle.getInt("position")

        Log.d(TAG, "DetailsFragment: processBundle: name=$name, pos=$pos")
        val curList = when(name){

            RES_NAME_ANDROID -> {
                viewModel.getAndroid()
            }

            RES_NAME_ALGORITHMS -> {
                viewModel.getAlgorithms()
            }

            RES_NAME_OTHERS -> {
                viewModel.getOthers()
            }

            else -> return
        }

        title = curList[pos].itemTitle
        link = curList[pos].itemLink
        des = curList[pos].itemDescription
        bindViews()
    }

    companion object {
        const val RES_NAME_ANDROID = "android"
        const val RES_NAME_ALGORITHMS = "algorithms"
        const val RES_NAME_OTHERS = "others"
    }
}

























