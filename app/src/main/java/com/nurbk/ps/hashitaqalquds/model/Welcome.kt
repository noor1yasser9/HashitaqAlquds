package com.nurbk.ps.hashitaqalquds.model

import android.content.Context
import com.nurbk.ps.hashitaqalquds.R


data class Welcome(var image: Int, var title: String = "", var desc: String = "")


fun getData(context: Context) =
    arrayListOf(
        Welcome(
            R.drawable.w1,
            context.getString(R.string.w1)
        ),
        Welcome(
            R.drawable.w2,
            context.getString(R.string.w2)
        ),
        Welcome(
            R.drawable.w3,
            context.getString(R.string.w3)
        )


    )