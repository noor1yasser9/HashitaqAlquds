package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.nurbk.ps.hashitaqalquds.util.Result
@HiltViewModel
class HomeViewModel @Inject constructor(
    val postRepository: PostRepository,
    application: Application
) : AndroidViewModel(application) {

    //post live data

    private val deletePostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val addLikeLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val addCommentLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getAllPostWhereUserIdLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getLikesLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getCommentsLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getAllPostsLiveData: MutableLiveData<Result<Any>> = MutableLiveData()


    //post function


    fun deletePost(postId: String) {
        deletePostLiveData.postValue(Result.loading(""))
        postRepository.delete(postId).addOnFailureListener {
            deletePostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            deletePostLiveData.postValue(Result.success(it))
        }

    }

    fun addLike(postId: String, userId: String) {
        addLikeLiveData.postValue(Result.loading(""))
        postRepository.addLike(postId, userId).addOnFailureListener {
            addLikeLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            addLikeLiveData.postValue(Result.success(it))
        }

    }

    fun addComment(postId: String, post: Post) {
        addCommentLiveData.postValue(Result.loading(""))
        postRepository.addComment(postId, post).addOnFailureListener {
            addCommentLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            addCommentLiveData.postValue(Result.success(it))
        }

    }

    fun getAllPosts() {
        getAllPostsLiveData.postValue(Result.loading(""))
        postRepository.getAllPosts().addOnFailureListener {
            getAllPostsLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            val array = arrayListOf<Post>()
            it.forEach { p -> array.add(p.toObject(Post::class.java)) }
            getAllPostsLiveData.postValue(Result.success(array))
        }

    }

    fun getAllPostWhereUserId(userId: String) {
        getAllPostWhereUserIdLiveData.postValue(Result.loading(""))
        postRepository.getAllWhereUserId(userId).addOnFailureListener {
            getAllPostWhereUserIdLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            val array = arrayListOf<Post>()
            it.forEach { p -> array.add(p.toObject(Post::class.java)) }
            getAllPostWhereUserIdLiveData.postValue(Result.success(array))
        }

    }

    fun getLikes(postId: String) {
        getLikesLiveData.postValue(Result.loading(""))
        postRepository.getLikes(postId).addOnFailureListener {
            getLikesLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            // val array = arrayListOf<Post>()
            //it.forEach { p-> array.add(p.toObject(Post::class.java)) }
            //   getLikesLiveData.postValue(Result.success(array))
        }

    }

    fun getComments(postId: String) {
        getCommentsLiveData.postValue(Result.loading(""))
        postRepository.getComments(postId).addOnFailureListener {
            getCommentsLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            val array = arrayListOf<Post>()
            it.forEach { p -> array.add(p.toObject(Post::class.java)) }
            getCommentsLiveData.postValue(Result.success(array))
        }

    }



    val deletePostGetLiveData get() = deletePostLiveData
    val addLikeGetLiveData get() = addLikeLiveData
    val addCommentGetLiveData get() = addCommentLiveData
    val getAllPostWhereUserIdGetLiveData get() = getAllPostWhereUserIdLiveData
    val getAllPostsGetLiveData get() = getAllPostsLiveData
    val getLikesGetLiveData get() = getLikesLiveData
    val getCommentsGetLiveData get() = getCommentsLiveData
}