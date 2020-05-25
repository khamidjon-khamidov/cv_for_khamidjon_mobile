package com.hamidjonhamidov.cvforkhamidjon.util

import android.content.Context

class TokenStoreManager(val ctx: Context){

    private val PREF_NAME = "PREF_NAME24342"
    private val TOKEN_KEY = "TOKEN_KEY"
    private val LAST_ORDER_KEY = "LAST_ORDER_KEY"
    private val RESTRICT_USER_ORDER_KEY = "RESTRICT_USER_ORDER_KEY"

    fun restrictUser(){
        val sharedPrefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putBoolean(RESTRICT_USER_ORDER_KEY, true)
        editor.apply()
    }

    fun unrestrictUser(){
        val sharedPrefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putBoolean(RESTRICT_USER_ORDER_KEY, false)
        editor.apply()
    }

    fun isRestricted(): Boolean{
        val sharedPrefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(RESTRICT_USER_ORDER_KEY, false)
    }

    fun storeToken(token: String): Boolean {
        val sharedPrefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
        return true
    }

    fun getToken(): String?{
        val sharedPrefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getString(TOKEN_KEY, null)
    }

    fun saveLastOrderNumber(num: Int): Boolean{
        val sharedPrefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putInt(LAST_ORDER_KEY, num)
        editor.apply()
        return true
    }

    fun getLasOrderNum(): Int{
        val sharedPrefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getInt(LAST_ORDER_KEY, 1)
    }

}