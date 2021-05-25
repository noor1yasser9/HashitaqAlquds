package com.nurbk.ps.hashitaqalquds.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.IS_LOGIN
import com.nurbk.ps.hashitaqalquds.other.PREFERENCES_NAME
import com.nurbk.ps.hashitaqalquds.other.USER_PROFILE

class PreferencesManager(private val mContext: Context) {
    private val sharedPreferences: SharedPreferences =
        mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val user: User
        get() {
            val user = sharedPreferences.getString(USER_PROFILE, Gson().toJson(User()))
            return Gson().fromJson(user, User::class.java)
        }
    val isLogin: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGIN, false)

    fun logOut() {
        editor.clear().apply()
    }

    @Volatile
    private var instance: PreferencesManager? = null
    private val LOCK = Any()

    operator fun invoke(mContext: Context) =
        instance ?: synchronized(LOCK) {
            instance ?: PreferencesManager(mContext).also {
                instance = it
            }
        }

    private fun createPreferencesManager(mContext: Context) = PreferencesManager(mContext)

}
