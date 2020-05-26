package com.hamidjonhamidov.cvforkhamidjon.ui.a_main.e_projects

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel
import com.hamidjonhamidov.cvforkhamidjon.ui.goToLink
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.BaseMainFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.getCurrentViewStateOrNew
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.ui.showToast
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.ProjectItemListener
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.ProjectsAdapter
import kotlinx.android.synthetic.main.fragment_projects.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class ProjectsFragment(
    viewModelProvider: ViewModelProvider.Factory,
    val projectStateEvent: MainStateEvent
) : ProjectItemListener, BaseMainFragment<ProjectModel>(
    R.layout.fragment_projects,
    R.menu.only_refresh_menu,
    viewModelProvider,
    projectStateEvent
) {

    private val TAG = "AppDebug"
    
    lateinit var listAdapter: ProjectsAdapter

    override fun subscribeDataObservers() {
        // observe data in about me
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.projectsFragmentView?.let {
                it.projects?.let { projects ->
                    updateView(projects)
                }
            }
        })
    }

    override fun initData() {
        projects_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ProjectsFragment.context)
            listAdapter = ProjectsAdapter(this@ProjectsFragment)
            adapter = listAdapter
        }

        if (viewModel.getCurrentViewStateOrNew().projectsFragmentView.projects == null &&
            !viewModel.jobManger.isJobActive(projectStateEvent.responsibleJob)
        ) {
            viewModel.setStateEvent(projectStateEvent)
        }

        updateView(viewModel.getCurrentViewStateOrNew().projectsFragmentView.projects)

    }

    override fun updateView(myModel: ProjectModel?) {}

    override fun updateView(modelList: List<ProjectModel>?) {
        modelList?.let{
            listAdapter.submitList(modelList)
        }
    }

    override fun onSourceCodeClick(position: Int) {
        viewModel.getCurrentViewStateOrNew().projectsFragmentView.projects?.let {
            if (it[position].projectGitLink != "")
                activity?.goToLink(it[position].projectGitLink)
            else activity?.showToast("Sorry, data unavailable")
        }
    }

    override fun onAppCodeClick(position: Int) {
        viewModel.getCurrentViewStateOrNew().projectsFragmentView.projects?.let {
            if (it[position].projectLink != "")
                activity?.goToLink(it[position].projectLink)
            else activity?.goToLink(it[position].projectLink)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ProjectsFragment: onDestroy: ")
    }

}
