package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel

class ProjectsAdapter(
    val listener: ProjectItemListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProjectModel>(){
        override fun areContentsTheSame(oldItem: ProjectModel, newItem: ProjectModel): Boolean {
            return newItem == oldItem
        }

        override fun areItemsTheSame(oldItem: ProjectModel, newItem: ProjectModel): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProjectsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.project_list_item,
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ProjectsViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ProjectModel>){
        differ.submitList(list)
    }
}
