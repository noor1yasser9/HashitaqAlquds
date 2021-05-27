package com.nurbk.ps.hashitaqalquds.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String = "",
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val image: String = "",
    val bio: String = "",
) : Parcelable
