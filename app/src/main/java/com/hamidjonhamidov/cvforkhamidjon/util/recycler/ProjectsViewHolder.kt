package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.ProjectModel

class ProjectsViewHolder(
    itemView: View,
    val projectItemListener: ProjectItemListener
) : RecyclerView.ViewHolder(itemView) {

    private val TAG = "AppDebug"

    fun bind(item: ProjectModel) = with(item) {
        // bind title
        val titleTv = itemView.findViewById<TextView>(R.id.projects_tv_title)
        titleTv.text = projectTitle

        // bind project des
        val desTv = itemView.findViewById<TextView>(R.id.projects_tv_des)
        desTv.text = projectDes


        // bind time
        val timeTv = itemView.findViewById<TextView>(R.id.projects_tv_time)
        timeTv.text = projectTime

        // bind buttons
        itemView.findViewById<Button>(R.id.projects_btn_source_id).setOnClickListener{
            projectItemListener.onSourceCodeClick(layoutPosition)
        }

        itemView.findViewById<Button>(R.id.projects_btn_app).setOnClickListener {
            projectItemListener.onAppCodeClick(layoutPosition)
        }

        itemView.apply {
            alpha = 0.toFloat()

            animate()
                .alpha(1.toFloat())
                .setDuration(2000L)
                .setListener(null)
        }
    }


}

interface ProjectItemListener{
    fun onSourceCodeClick(position: Int)

    fun onAppCodeClick(position: Int)
}














