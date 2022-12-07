package com.capstone.core.utils

import android.content.Context
import com.capstone.core.domain.model.User
import kotlin.math.PI

class MySharedPreference(context: Context) {

    companion object {
        private const val PREFS_NAME = "prefs name"
        private const val TOKEN = "token"
        private const val LOGIN = "login"
        private const val FCM = "fcm"

        // User
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
        private const val ROLE = "role"
        private const val PICTURE = "picture"
        private const val OTP = "otp"
        private const val ANONYMOUS = "anonymous"
        private const val IS_PREMIUM = "is_premium"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setToken(value: String){
        val editor = prefs.edit()
        editor.putString(TOKEN, value)
        editor.apply()
    }

    fun getToken(): String =
        prefs.getString(TOKEN, "").toString()

    fun setFcm(value: String){
        val editor = prefs.edit()
        editor.putString(FCM, value)
        editor.apply()
    }

    fun getFcm(): String =
        prefs.getString(FCM, "").toString()

    fun setLogin(value: Boolean){
        val editor = prefs.edit()
        editor.putBoolean(LOGIN, value)
        editor.apply()
    }

    fun getLogin(): Boolean =
        prefs.getBoolean(LOGIN, false)

    fun setUser(data: User){
        val editor = prefs.edit()
        editor.apply {
            putInt(ID, data.id)
            putString(NAME, data.name)
            putString(EMAIL, data.email)
            putString(PHONE, data.phone)
            putInt(ROLE, data.role)
            putString(PICTURE, data.picture)
            putInt(OTP, data.otp)
            putBoolean(IS_PREMIUM, data.is_premium!!)
            apply()
        }
    }

    fun getUser(): User =
        User(
            id = prefs.getInt(ID, 0),
            name = prefs.getString(NAME, "").toString(),
            email = prefs.getString(EMAIL, "").toString(),
            phone = prefs.getString(PHONE, "").toString(),
            picture = prefs.getString(PICTURE, "").toString(),
            role = prefs.getInt(ROLE, 0),
            otp = prefs.getInt(OTP, 0),
            is_premium = prefs.getBoolean(IS_PREMIUM, false)
        )

    fun setAnonymous(data: Boolean) {
        val editor = prefs.edit()
        editor.apply {
            putBoolean(ANONYMOUS, data)
            apply()
        }
    }

    fun getAnonymous(): Boolean = prefs.getBoolean(ANONYMOUS, false)

    fun logout(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}