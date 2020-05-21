package com.hamidjonhamidov.cvforkhamidjon.util.recycler

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hamidjonhamidov.cvforkhamidjon.R
import com.hamidjonhamidov.cvforkhamidjon.util.DoublePostModel
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager


class PostViewHolder(
    itemView: View,
    val postItemListener: PostItemListener? = null,
    val context: Context,
    val glideManager: GlideManager
) : RecyclerView.ViewHolder(itemView) {

    private val TAG = "AppDebug"

    fun bind(doubleItem: DoublePostModel) = with(doubleItem) {

        // variables
        val front_anim: AnimatorSet
        val back_anim: AnimatorSet
        val cameraScale = context.resources.displayMetrics.density

        // ****** initializing anim variables *********
        val card_back = itemView.findViewById<ConstraintLayout>(R.id.post_card_back)
        card_back.cameraDistance = 8000 * cameraScale
        back_anim = AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet

        val card_front = itemView.findViewById<ConstraintLayout>(R.id.post_card_front)
        card_front.cameraDistance = 8000 * cameraScale
        front_anim =
            AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet


        // ********** front *******
        val func = {
            front_anim.setTarget(card_front)
            back_anim.setTarget(card_back)
            front_anim.start()
            back_anim.start()
        }

        with(postModel1) {
            // bind picture
            val img1 = itemView.findViewById<ImageView>(R.id.post_img_front)
            glideManager.setImage(postLink, img1)

            // bind title
            val title1 = itemView.findViewById<TextView>(R.id.post_tv__title_front)
            title1.text = postDescription



            // bind button
            val button = itemView.findViewById<ImageButton>(R.id.post_btn_rotate_front)
            button.setOnClickListener {
                func.invoke()
            }

        }


        // ********** back ********
        postModel2?.let {
            // bind picture
            val img1 = itemView.findViewById<ImageView>(R.id.post_img_back)
            glideManager.setImage(it.postLink, img1)

            // bind title
            val title1 = itemView.findViewById<TextView>(R.id.post_tv__title_back)
            title1.text = it.postDescription
        }

        // bind button
        itemView.findViewById<ImageButton>(R.id.post_btn_rotate_back).setOnClickListener {
            front_anim.setTarget(card_back)
            back_anim.setTarget(card_front)
            back_anim.start()
            front_anim.start()
        }

        // animate item view
        itemView.apply {
            alpha = 0.toFloat()

            animate()
                .alpha(1.toFloat())
                .setDuration(2000L)
                .setListener(null)
        }

        func.invoke()
        Unit
    }

}

interface PostItemListener {
    fun onRotateClick(position: Int)
}
