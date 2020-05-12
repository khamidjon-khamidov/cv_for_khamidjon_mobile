package com.hamidjonhamidov.cvforkhamidjon.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import javax.inject.Inject

class NetworkConnection
@Inject
constructor(
    val application: Application
) {

    private val TAG = "AppDebug"

    fun isConectedToInternet(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            return cm.activeNetworkInfo?.isConnected ?: false
        } catch (e: Exception) {
            Log.e(TAG, "isConnectedToTheInternet: ${e.message}")
        }
        return false
    }
}