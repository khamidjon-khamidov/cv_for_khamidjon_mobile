package com.hamidjonhamidov.cvforkhamidjon

import android.app.Application
import com.hamidjonhamidov.cvforkhamidjon.di.AppComponent
import com.hamidjonhamidov.cvforkhamidjon.di.DaggerAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@InternalCoroutinesApi
open class MyApplication : Application() {

    private val TAG = "AppDebug"

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(this)
    }

}