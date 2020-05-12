package com.hamidjonhamidov.cvforkhamidjon.util.glide

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hamidjonhamidov.cvforkhamidjon.util.glide.GlideManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlideRequestManager
@Inject
constructor(
    private val requestManager: RequestManager
) : GlideManager {
    override fun setImage(imageUrl: String, imageView: ImageView) {
        requestManager
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

}