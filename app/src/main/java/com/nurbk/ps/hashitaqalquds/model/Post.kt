package com.nurbk.ps.hashitaqalquds.model

data class Post(
    val id: String = "",
    val userId:String="",
    val userName:String="",
    val userImage:String="",
    val date: String = "",
    val tag: String = "",
    val content: String = "",
    val media: String = ""
)
