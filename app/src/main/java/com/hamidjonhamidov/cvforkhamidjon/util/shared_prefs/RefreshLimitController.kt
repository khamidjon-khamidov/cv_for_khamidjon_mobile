package com.hamidjonhamidov.cvforkhamidjon.util.shared_prefs

interface RefreshLimitController {

    // setting and getting last update time in  days
    fun incrementHomeSyncTimes()
    fun canHomeSync(): Boolean

    // setting and getting last update time in  days
    fun incrementAboutMeSyncTimes()
    fun canAboutMeSync(): Boolean

    // setting and getting last update time in  days
    fun incrementMySkillsSyncTimes()
    fun canMySkillsSync(): Boolean

    // setting and getting last update time in  days
    fun incrementAchievmentsSyncTimes()
    fun canAchievmentsSync(): Boolean

    // setting and getting last update time in  days
    fun incrementProjectsSyncTime()
    fun canProjectsSync(): Boolean
}