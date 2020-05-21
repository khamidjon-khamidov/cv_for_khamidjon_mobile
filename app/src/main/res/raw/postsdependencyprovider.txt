package com.hamidjonhamidov.whoiskhamidjon.ui.posts

import com.bumptech.glide.RequestManager
import com.hamidjonhamidov.whoiskhamidjon.util.ViewModelProviderFactory

interface PostsDependencyProvider {

    fun getVMProviderFactory(): ViewModelProviderFactory

    fun getGlideRequestManager(): RequestManager
}