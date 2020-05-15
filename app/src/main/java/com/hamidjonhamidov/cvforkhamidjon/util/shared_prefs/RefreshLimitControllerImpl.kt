package com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs

import android.app.Application
import android.content.Context
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.SharedPrefConstants.ABOUTME_PREF_KEY
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.SharedPrefConstants.ACHIEVEMENTS_PREF_KEY
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.SharedPrefConstants.DAILY_REFRESH_LIMIT
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.SharedPrefConstants.HOME_PREF_KEY
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.SharedPrefConstants.MYSKILLS_PREF_KEY
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.SharedPrefConstants.PROJECTS_PREF_KEY
import com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs.SharedPrefConstants.SHARED_PREF_KEY
import javax.inject.Inject
import javax.inject.Singleton

/*
* 1) This class enables to identify whether remote synchronisation
* is allowed or not
* 2) Long value saved in storage saves refresh time in the last digit
* and the rest digits saves last saved time
 */

@Singleton
class RefreshLimitControllerImpl
@Inject
constructor(
    application: Application
) : RefreshLimitController{

    private val sharedPreferences =
        application.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)

    override fun incrementHomeSyncTimes() {
        incrementSyncTime(HOME_PREF_KEY)
    }

    override fun canHomeSync() =
        canSync(HOME_PREF_KEY)

    override fun incrementAboutMeSyncTimes() {
        incrementSyncTime(ABOUTME_PREF_KEY)
    }

    override fun canAboutMeSync() =
        canSync(ABOUTME_PREF_KEY)


    override fun incrementMySkillsSyncTimes() {
        incrementSyncTime(MYSKILLS_PREF_KEY)
    }

    override fun canMySkillsSync() =
        canSync(MYSKILLS_PREF_KEY)


    override fun incrementAchievmentsSyncTimes() {
        incrementSyncTime(ACHIEVEMENTS_PREF_KEY)
    }

    override fun canAchievmentsSync() =
        canSync(ACHIEVEMENTS_PREF_KEY)

    override fun incrementProjectsSyncTime() {
        incrementSyncTime(PROJECTS_PREF_KEY)
    }

    override fun canProjectsSync() =
        canSync(PROJECTS_PREF_KEY)


    // this function increments refresh times
    private fun incrementSyncTime(key: String){
        val getLastRefreshTime = getLastRefreshTime(key)
        val storeValue: Long

        // if last refresh time is 0 or on day has already passed, get current time and increment it
        if(getLastRefreshTime==0L || isOneDayPassed(getLastRefreshTime/10)){
            storeValue = System.currentTimeMillis() * 10L + 1
        }
        // otherwise increment last refresh time
        else {
            storeValue = getLastRefreshTime+1
        }

        setLastRefreshTime(key, storeValue)
    }

    private fun getLastRefreshTime(key: String) =
        sharedPreferences.getLong(key, 0)

    private fun setLastRefreshTime(key: String, value: Long){
        with(sharedPreferences.edit()){
            putLong(key, value)
            apply()
        }
    }

    private fun canSync(key: String): Boolean {
        val lastRefreshTime = getLastRefreshTime(key)
        // if last refresh time is 0, then we can freely refresh
        if(lastRefreshTime==0L){
            return true
        }
        // if one day has already passed, set time to 0 and return true
        else if (isOneDayPassed(lastRefreshTime/10)) {
            setLastRefreshTime(key, 0)
            return true
        }
        // if one day has not passed but there are allowed refresh times return true
        else if(lastRefreshTime%10 < DAILY_REFRESH_LIMIT){
            return true
        }
        // if one day has passed, refresh limit finished, then return false
        else
            return false
    }

    private fun isOneDayPassed(lasRefreshTimeInMilliseconds: Long) =
        // this returns true if it has been more than a day
        (System.currentTimeMillis() - lasRefreshTimeInMilliseconds) > 86_400_000L
}