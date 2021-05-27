package com.nurbk.ps.hashitaqalquds.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class NotificationParent(
    val data: Post = Post(),
    val to: String
)
