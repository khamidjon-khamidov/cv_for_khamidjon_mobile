package com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.b_algorithms

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
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail.DetailsFragment.Companion.RES_NAME_ALGORITHMS
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail.DetailsFragment.Companion.RES_NAME_ANDROID
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.d_detail.DetailsFragmentArgs
import com.hamidjonhamidov.cvforkhamidjon.ui.b_achievments.viewmodel.AchievementsViewModel
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.AchievementAdapter
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.AchievementListener
import kotlinx.android.synthetic.main.fragment_algorithms.*
import kotlinx.android.synthetic.main.fragment_algorithms.no_iv_data
import kotlinx.android.synthetic.main.fragment_my_skills.*
import kotlinx.android.synthetic.main.fragment_others.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AlgorithmsFragment(
    viewModelFactory: ViewModelProvider.Factory,
    val glideManager: GlideManager
) : Fragment(R.layout.fragment_algorithms), AchievementListener {

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
        algorithms_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@AlgorithmsFragment.context)
            listAdapter = AchievementAdapter(this@AlgorithmsFragment, glideManager)
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
                    updateView(it[1])
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
            "resName" to RES_NAME_ALGORITHMS,
            "position" to position
        )
        findNavController().navigate(R.id.action_algorithmsFragment_to_detailsFragment2, bundle)
    }
}
