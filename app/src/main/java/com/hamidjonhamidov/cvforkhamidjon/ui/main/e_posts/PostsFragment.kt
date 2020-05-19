package com.hamidjonhamidov.cvforkhamidjon.ui.main.e_posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel
import com.hamidjonhamidov.cvforkhamidjon.ui.main.BaseMainFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.getCurrentViewStateOrNew
import com.hamidjonhamidov.cvforkhamidjon.ui.main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.PostAdapter
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@FlowPreview
@InternalCoroutinesApi
class PostsFragment(
    viewModelProvider: ViewModelProvider.Factory,
    val glideManager: GlideManager,
    val postsStateEvent: MainStateEvent
) : BaseMainFragment<PostModel>(R.layout.fragment_posts, R.menu.only_refresh_menu, viewModelProvider, postsStateEvent) {

    private val TAG = "AppDebug"

    lateinit var listAdapter: PostAdapter

    override fun subscribeDataObservers() {
        // observe data in about me
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.postsFragmentView?.let {
                it.posts?.let { posts ->
                    updateView(posts)
                }
            }
        })
    }

    override fun initData() {
        posts_recycler_view.apply{
            layoutManager = LinearLayoutManager(this@PostsFragment.context)
            listAdapter = PostAdapter(null, requireContext(), glideManager)
            adapter = listAdapter
        }

        if (viewModel.getCurrentViewStateOrNew().postsFragmentView.posts == null &&
            !viewModel.jobManger.isJobActive(postsStateEvent.responsibleJob)
        ) {
            viewModel.setStateEvent(postsStateEvent)
        } else {
            updateView(viewModel.getCurrentViewStateOrNew().postsFragmentView.posts!!)
        }

    }

    override fun updateView(myModel: PostModel?) {

    }

    override fun updateView(modelList: List<PostModel>) {
        Log.d(TAG, "PostsFragment: updateView: size = ${modelList.size}")
        listAdapter.submitList(modelList)
    }

}
