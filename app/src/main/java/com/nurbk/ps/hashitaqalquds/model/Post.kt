package com.nurbk.ps.hashitaqalquds.model

import android.text.format.DateFormat
import com.google.firebase.firestore.Exclude
import com.google.gson.annotations.Expose

data class Post(
    val id: String = "",
    val userId: String = "",
    val date: Long = 0,
    val tag: String = "",
    val content: String = "",
    var media: String = "",
    val type: Int = 0,
    @Exclude @Expose(deserialize = false, serialize = false)
    var users: User = User()
){
    @Exclude
    fun formatDate(): String {
        return DateFormat.format("yyyy-MM-dd hh:mm:ss a", date).toString()
    }

}
