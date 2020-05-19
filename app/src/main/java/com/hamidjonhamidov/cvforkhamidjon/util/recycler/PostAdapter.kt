package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel
import com.hamidjonhamidov.cvforkhamidjon.util.DoublePostModel
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostAdapter(
    val listener: PostItemListener? = null,
    val context: Context,
    val glideManager: GlideManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DoublePostModel>(){
        override fun areContentsTheSame(oldItem: DoublePostModel, newItem: DoublePostModel): Boolean {
            return newItem == oldItem
        }

        override fun areItemsTheSame(oldItem: DoublePostModel, newItem: DoublePostModel): Boolean {
            return newItem.postModel1==oldItem.postModel1 && newItem.postModel2==oldItem.postModel2
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_item,
                parent,
                false
            ),
            listener,
            context,
            glideManager
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PostViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun submitList(list: List<DoublePostModel>){
        differ.submitList(list)
        preloadImages(list)
    }

    private fun preloadImages(list: List<DoublePostModel>) {
        CoroutineScope(Dispatchers.Default).launch {
            list.forEach{
                glideManager.preloadImg(it.postModel1.postLink)
                it.postModel2?.postLink?.let { glideManager.preloadImg(it) }
            }
        }
    }
}
