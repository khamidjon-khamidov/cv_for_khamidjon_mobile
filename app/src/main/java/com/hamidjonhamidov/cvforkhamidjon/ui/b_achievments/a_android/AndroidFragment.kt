package com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.a_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.AchievmentsActivity
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail.DetailsFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail.DetailsFragment.Companion.RES_NAME_ANDROID
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.AchievementsViewModel
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.AchievementAdapter
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.AchievementListener
import kotlinx.android.synthetic.main.fragment_android.*
import kotlinx.android.synthetic.main.fragment_android.no_iv_data
import kotlinx.android.synthetic.main.fragment_my_skills.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AndroidFragment(
    viewModelFactory: ViewModelProvider.Factory,
    val glideManager: GlideManager
) : Fragment(R.layout.fragment_android), AchievementListener {

    val viewModel: AchievementsViewModel by activityViewModels {
        viewModelFactory
    }

    lateinit var listAdapter: AchievementAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        subscribeObservers()
    }

    fun initData() {
        android_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@AndroidFragment.context)
            listAdapter = AchievementAdapter(this@AndroidFragment, glideManager)
            adapter = listAdapter
        }

        no_iv_data.setOnClickListener {
            YoYo.with(Techniques.Shake)
                .duration(1000)
                .repeat(2)
                .playOn(no_iv_data)
        }
    }

    fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.achievementsFragmentView?.let {
                it.achievements?.let {
                    updateView(it[0])
                }
            }
        })
    }

    private fun updateView(achievementModel: AchievementModel?) {
        achievementModel?.honorsList?.let {
            no_iv_data.visibility = View.GONE
            listAdapter.submitList(it)
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AchievmentsActivity).shouldRefresh()
    }

    override fun onAchievmentClick(position: Int) {
        val bundle = bundleOf(
            "resName" to RES_NAME_ANDROID,
            "position" to position
        )

        findNavController().navigate(R.id.action_androidFragment_to_detailsFragment, bundle)
    }

}
