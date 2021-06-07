package com.nurbk.ps.hashitaqalquds.model

import android.content.Context
import com.nurbk.ps.hashitaqalquds.R

data class Settings(val icon: Int, val name: String, var isSwitch: Boolean = false)

fun getDataStings(context: Context) =
    arrayListOf(
        Settings(
            R.drawable.ic_profile,
            context.getString(R.string.editProfile)
        ),
        Settings(
            R.drawable.ic_monito,
            context.getString(R.string.theme),
            true
        ),
        Settings(
            R.drawable.ic_info,
            context.getString(R.string.aboutApp)
        ),
        Settings(
            R.drawable.ic_exit,
            context.getString(R.string.logOut)
        )

    )