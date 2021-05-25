package com.nurbk.ps.hashitaqalquds.model

data class Post(
    val id: String = "",
    val userId: String = "",
    val date: Long = 0,
    val tag: String = "",
    val content: String = "",
    var media: String = "",
    val type: Int
) {
    companion object {
        const val PHOTO_TYPE = 0
        const val VIDEO_TYPE = 1
        const val PDF_TYPE = 2
        const val CONTENT_TYPE = 4
    }
}
