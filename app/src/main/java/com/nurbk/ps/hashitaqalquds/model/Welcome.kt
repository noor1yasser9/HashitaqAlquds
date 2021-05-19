package com.nurbk.ps.hashitaqalquds.model

import android.content.Context


data class Welcome(var image: Int = 0, var title: String = "", var desc: String = "")


fun getData(context: Context) =
    arrayListOf(
        Welcome(

        ),
        Welcome(

        ),
        Welcome()

    )