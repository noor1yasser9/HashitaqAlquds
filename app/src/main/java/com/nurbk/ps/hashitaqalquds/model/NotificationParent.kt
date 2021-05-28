package com.nurbk.ps.hashitaqalquds.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.w3c.dom.Comment

data class NotificationParent(
    val data: NotificationData = NotificationData(),
    val to: String = ""
)

data class NotificationData(val post: Post = Post(), val comment: Post = Post())