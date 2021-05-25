package com.nurbk.ps.hashitaqalquds.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nurbk.ps.hashitaqalquds.model.Post

class PostRepository private constructor() {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun insert(post: Post) = db.collection("posts").add(post)
    fun update(postId: String, post: Map<String, Any>) =
        db.collection("posts").document(postId).update(post)

    fun delete(postId: String) = db.collection("posts").document(postId).delete()
    fun addLike(postId: String, userId: String) =
        db.collection("posts").document(postId).collection("likes").add(userId)

    fun addComment(postId: String, post: Post) =
        db.collection("posts").document(postId).collection("comments").add(post)

    fun getAllPosts() = db.collection("posts").get()
    fun getAllWhereUserId(userId: String) =
        db.collection("posts").whereEqualTo("userId", userId).get()

    fun getLikes(postId: String) =
        db.collection("posts").document(postId).collection("likes").get()

    fun getComments(postId: String) =
        db.collection("posts").document(postId).collection("comments").get()

    companion object {
        @Volatile
        private var instance: PostRepository? = null
        private val LOCK = Any()

        operator fun invoke() =
            instance ?: synchronized(LOCK) {
                instance ?: createPostRepository().also {
                    instance = it
                }
            }

        private fun createPostRepository() = PostRepository()
    }
}