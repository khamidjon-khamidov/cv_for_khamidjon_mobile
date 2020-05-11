package com.hamidjonhamidov.cvforkhamidjon.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.hamidjonhamidov.cvforkhamidjon.data_requests.persistence.AppDatabase
import com.hamidjonhamidov.cvforkhamidjon.util.GlideManager
import com.hamidjonhamidov.cvforkhamidjon.util.GlideRequestManager
import com.hamidjonhamidov.cvforkhamidjon.util.constants.DATABASE_CONSTANTS
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule{

    @Singleton
    @Provides
    fun provideGlideRequestManager(
        application: Application
    ) : GlideManager =
        GlideRequestManager(
            Glide.with(application)
        )

    @Singleton
    @Provides
    fun provideAppDb(app: Application) =
        Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_CONSTANTS.APP_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
}
