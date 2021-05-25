package com.nurbk.ps.hashitaqalquds.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.other.COLLECTION_POST
import com.nurbk.ps.hashitaqalquds.util.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor() {


    private val insertPostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val updatePostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val deletePostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val addLikeLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val addCommentLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getAllPostWhereUserIdLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getAllPostsLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getLikesLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getCommentsLiveData: MutableLiveData<Result<Any>> = MutableLiveData()


    private val db by lazy {
        FirebaseFirestore.getInstance()
    }


    fun insert(post: Post) {
        insertPostLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).add(post).addOnFailureListener {
            insertPostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            insertPostLiveData.postValue(Result.success(it))
        }

    }


    fun update(postId: String, post: Map<String, Any>) {
        updatePostLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).update(post).addOnFailureListener {
            updatePostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            updatePostLiveData.postValue(Result.success(it))
        }
    }

    fun delete(postId: String) {
        deletePostLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).delete().addOnFailureListener {
            deletePostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            deletePostLiveData.postValue(Result.success(it))
        }
    }


    fun addLike(postId: String, userId: String) {
        addLikeLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).collection("likes").add(userId)
            .addOnFailureListener {
                addLikeLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                addLikeLiveData.postValue(Result.success(it))
            }

    }


    fun addComment(postId: String, post: Post) {
        addCommentLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).collection("comments").add(post)
            .addOnFailureListener {
                addCommentLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                addCommentLiveData.postValue(Result.success(it))
            }
    }

    fun getAllPosts() {
        getAllPostsLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).get().addOnFailureListener {
            getAllPostsLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            val array = arrayListOf<Post>()
            it.forEach { p -> array.add(p.toObject(Post::class.java)) }
            getAllPostsLiveData.postValue(Result.success(array))
        }
    }

    fun getAllWhereUserId(userId: String) {
        getAllPostWhereUserIdLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).whereEqualTo("userId", userId).get()
            .addOnFailureListener {
                getAllPostWhereUserIdLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                val array = arrayListOf<Post>()
                it.forEach { p -> array.add(p.toObject(Post::class.java)) }
                getAllPostWhereUserIdLiveData.postValue(Result.success(array))
            }
    }

    fun getLikes(postId: String) {
        getLikesLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).collection("likes").get()
            .addOnFailureListener {
            getLikesLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            // val array = arrayListOf<Post>()
            //it.forEach { p-> array.add(p.toObject(Post::class.java)) }
            //   getLikesLiveData.postValue(Result.success(array))
        }

    }

    fun getComments(postId: String) {
        getCommentsLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).collection("comments").get().addOnFailureListener {
            getCommentsLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            val array = arrayListOf<Post>()
            it.forEach { p -> array.add(p.toObject(Post::class.java)) }
            getCommentsLiveData.postValue(Result.success(array))
        }
    }

    //post getting live data
    val insertPostGetLiveData get() = insertPostLiveData
    val updatePostGetLiveData get() = updatePostLiveData
    val deletePostGetLiveData get() = deletePostLiveData
    val addLikeGetLiveData get() = addLikeLiveData
    val addCommentGetLiveData get() = addCommentLiveData
    val getAllPostWhereUserIdGetLiveData get() = getAllPostWhereUserIdLiveData
    val getAllPostsGetLiveData get() = getAllPostsLiveData
    val getLikesGetLiveData get() = getLikesLiveData
    val getCommentsGetLiveData get() = getCommentsLiveData


}
