package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.models.offline.main.SkillModel
import com.hamidjonhamidov.cvforkhamidjon.util.WeightWrapper

class SkillViewHolder(
    itemView: View,
    val context: Context
) : RecyclerView.ViewHolder(itemView) {

    private val TAG = "AppDebug"

    fun bind(item: SkillModel) = with(item) {
        // tv for skill title
        val titleTv = itemView.findViewById<TextView>(R.id.skills_tv_skill_item_title)
        // tv for positive progress bar
        val posProgTv = itemView.findViewById<TextView>(R.id.skills_tv_progress_pos)
        val negProgTv = itemView.findViewById<TextView>(R.id.skills_tv_progress_neg)
        // tv for skills list
        val skillsTv = itemView.findViewById<TextView>(R.id.skills_tv_item)


        // ************* Title ****************
        titleTv.text = name


        // ********** Progress Bar ************
        // change tvs to wrapper class to animate their weight
        val posTv = WeightWrapper(posProgTv)
        val negTv = WeightWrapper(negProgTv)

        // animate weights of progress bar
        animateViewWeight(percentage.toFloat(), posTv)
        Log.d(TAG, "SkillViewHolder: bind: $percentage")
        animateViewWeight((100-percentage).toFloat(), negTv)


        // ************** Skills List ************
        skillsTv.text = skillsList.reduce { acc, s -> "$acc    $s" }

        // when item clicked, change its visibility
        itemView.setOnClickListener {

            val anim: Animation
            if (skillsTv.visibility == View.VISIBLE) {
                anim = AnimationUtils.loadAnimation(context, R.anim.slide_down_anim)
                skillsTv.visibility = View.GONE
                skillsTv.animation = anim
            } else {
                anim = AnimationUtils.loadAnimation(context, R.anim.slide_up_anim)
                skillsTv.visibility = View.VISIBLE
                skillsTv.animation = anim
            }
            skillsTv.animation = anim
        }
    }

    private fun animateViewWeight(
        toWeight: Float,
        animationWrapper: WeightWrapper
    ) {
        val anim = ObjectAnimator.ofFloat(
            animationWrapper,
            "weight",
            animationWrapper.getWeight(),
            toWeight
        )
        anim.setDuration(1500)
        anim.start()
    }
}

interface SkillClickListener {
    fun itemClick(post: Int, view: View)
}














