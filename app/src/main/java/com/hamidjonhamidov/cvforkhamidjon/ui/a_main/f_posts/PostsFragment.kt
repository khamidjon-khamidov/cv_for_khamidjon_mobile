package com.hamidjonhamidov.cvforkhamidjon.ui.a_main.f_posts

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.BaseMainFragment
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.getCurrentViewStateOrNew
import com.hamidjonhamidov.cvforkhamidjon.ui.a_main.viewmodel.state.MainStateEvent
import com.hamidjonhamidov.cvforkhamidjon.util.DoublePostModel
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.util.recycler.PostAdapter
import kotlinx.android.synthetic.main.fragment_my_skills.*
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_posts.no_iv_data
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
) : BaseMainFragment<PostModel>(
    R.layout.fragment_posts,
    R.menu.only_refresh_menu,
    viewModelProvider,
    postsStateEvent
) {

    private val TAG = "AppDebug"

    lateinit var listAdapter: PostAdapter

    override fun subscribeDataObservers() {
        // observe data in about me
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState?.postsFragmentView?.let {
                it.doublePosts?.let { posts ->
                    updateRecylerView(posts)
                }
            }
        })
    }

    override fun initData() {
        posts_recycler_view.apply {
            layoutManager = LinearLayoutManager(this@PostsFragment.context)
            listAdapter = PostAdapter(null, requireContext(), glideManager)
            adapter = listAdapter
        }

        no_iv_data.setOnClickListener {
            YoYo.with(Techniques.Shake)
                .duration(1000)
                .repeat(2)
                .playOn(no_iv_data)
        }

        if (viewModel.getCurrentViewStateOrNew().postsFragmentView.posts == null &&
            !viewModel.jobManger.isJobActive(postsStateEvent.responsibleJob)
        ) {
            viewModel.setStateEvent(postsStateEvent)
        }

        updateRecylerView(viewModel.getCurrentViewStateOrNew().postsFragmentView.doublePosts)
    }

    override fun updateView(myModel: PostModel?) {

    }

    override fun updateView(modelList: List<PostModel>?) {}

    fun updateRecylerView(doubleList: List<DoublePostModel>?) {
        doubleList?.let {
            no_iv_data.visibility = View.GONE
            listAdapter.submitList(doubleList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "PostsFragment: onDestroy: ")
    }
}
