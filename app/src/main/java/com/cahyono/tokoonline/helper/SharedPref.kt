package com.cahyono.tokoonline.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPref(activity: Activity) {

    val login = "login"

    private lateinit var sharedPreferences: SharedPreferences
    val mypref = "MY_PREF"

    init {
        sharedPreferences = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status:Boolean) {
        sharedPreferences.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin():Boolean {
        return sharedPreferences.getBoolean(login, false)
    }
}