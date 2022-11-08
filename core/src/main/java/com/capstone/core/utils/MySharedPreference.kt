package com.capstone.core.utils

import android.content.Context

class MySharedPreference(context: Context) {

    companion object {
        private const val PREFS_NAME = "prefs name"
        private const val TOKEN = "token"
        private const val LOGIN = "login"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setToken(value: String){
        val editor = prefs.edit()
        editor.putString(TOKEN, value)
        editor.apply()
    }

    fun getToken(): String =
        prefs.getString(TOKEN, "").toString()

    fun setLogin(value: Boolean){
        val editor = prefs.edit()
        editor.putBoolean(LOGIN, value)
        editor.apply()
    }

    fun getLogin(): Boolean =
        prefs.getBoolean(LOGIN, false)

    fun logout(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}