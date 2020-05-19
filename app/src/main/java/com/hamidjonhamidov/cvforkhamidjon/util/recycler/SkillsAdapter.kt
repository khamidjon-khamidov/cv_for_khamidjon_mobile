package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager

class SkillsAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SkillModel>(){
        override fun areContentsTheSame(oldItem: SkillModel, newItem: SkillModel): Boolean {
            return newItem == oldItem
        }

        override fun areItemsTheSame(oldItem: SkillModel, newItem: SkillModel): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SkillViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.skill_item_layout,
                parent,
                false
            ),
            parent.context
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SkillViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<SkillModel>){
        differ.submitList(list)
    }
}



















