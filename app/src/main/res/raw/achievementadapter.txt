package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.AchievementModel
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.Honor
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager

class AchievementAdapter(
    val listener: AchievementListener,
    val glideManager: GlideManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Honor>(){
        override fun areContentsTheSame(oldItem: Honor, newItem: Honor): Boolean {
            return newItem == oldItem
        }

        override fun areItemsTheSame(oldItem: Honor, newItem: Honor): Boolean {
            return newItem.itemId==oldItem.itemId
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AchievementViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.achievement_item,
                parent,
                false
            ),
            glideManager,
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AchievementViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    fun submitList(list: List<Honor>){
        differ.submitList(list)

    }
}
