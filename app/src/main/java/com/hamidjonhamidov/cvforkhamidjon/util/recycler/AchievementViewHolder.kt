package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.achievements.Honor
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager

class AchievementViewHolder(
    itemView: View,
    val glideManager: GlideManager,
    val achievmentListener: AchievementListener
) : RecyclerView.ViewHolder(itemView){

    private val TAG = "AppDebug"

    fun bind(item: Honor) = with(itemView){
        // bind vals
        val imgIv = findViewById<ImageView>(R.id.achiev_iv_image)
        val titleTv = findViewById<TextView>(R.id.achiev_tv_title)
        val desTv = findViewById<TextView>(R.id.achiev_tv_des)
        val backgr = findViewById<TextView>(R.id.achiev_tv_backg)

        // bind img
        glideManager.setImage(item.itemLink, imgIv)

        // bind title
        titleTv.text = item.itemTitle

        // bind des
        desTv.text = item.itemDescription

        // set listener
        backgr.setOnClickListener {
            achievmentListener.onAchievmentClick(layoutPosition)
        }

        Unit
    }
}

interface AchievementListener{
    fun onAchievmentClick(position: Int)
}