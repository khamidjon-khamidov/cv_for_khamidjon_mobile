package com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs

interface RefreshLimitController {

    // setting and getting last update time in days
    fun <DestinationViewEvent : Any> incrementSyncTime(dest: DestinationViewEvent)

    fun <DestinationViewEvent : Any> canSync(dest: DestinationViewEvent): Boolean

}