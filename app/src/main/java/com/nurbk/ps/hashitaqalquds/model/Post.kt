package com.nurbk.ps.hashitaqalquds.model

data class Post(
    val id: String = "",
    val userId: String = "",
    val date: Long = 0,
    val tag: String = "",
    val content: String = "",
    var media: String = "",
    val type: Int=0
)
