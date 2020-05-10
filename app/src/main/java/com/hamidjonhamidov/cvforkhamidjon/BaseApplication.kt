package com.hamidjonhamidov.cvforkhamidjon

import android.app.Application
import com.hamidjonhamidov.cvforkhamidjon.di.AppComponent
import com.hamidjonhamidov.cvforkhamidjon.di.DaggerAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
open class BaseApplication : Application() {

    private val TAG = "AppDebug"


    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    open fun initAppComponent() {
        appComponent =
            DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }
}