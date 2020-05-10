package com.hamidjonhamidov.cvforkhamidjon.di

import android.app.Application
import com.bumptech.glide.Glide
import com.hamidjonhamidov.cvforkhamidjon.util.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.util.GlideRequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule{

    @JvmStatic
    @Singleton
    @Provides
    fun provideGlideRequestManager(
        application: Application
    ) : GlideManager =
        GlideRequestManager(
            Glide.with(application)
        )
}
