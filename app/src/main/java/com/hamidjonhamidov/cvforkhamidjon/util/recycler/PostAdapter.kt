package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.PostModel
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager

class PostAdapter(
    val listener: PostItemListener? = null,
    val context: Context,
    val glideManager: GlideManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostModel>(){
        override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
                return false
//            return newItem == oldItem
        }

        override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
            return false
//            return newItem == oldItem
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
                holder.bind(differ.currentList, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return (differ.currentList.size+1)/2
    }

    fun submitList(list: List<PostModel>){
        differ.submitList(list)
    }
}
