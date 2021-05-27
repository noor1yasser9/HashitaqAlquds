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


    fun update(postId: String, post: Map<String, Any>) {
        postRepository.update(postId, post)
    }
    fun deletePost(postId: String) {
        postRepository.delete(postId)
    }
    fun addLike(postId: String, userArray: ArrayList<String>) {
        postRepository.addLike(postId,userArray)
    }

    fun getAllPostWhereUserId(userId: String) {
        postRepository.getAllWhereUserId(userId)
    }
    fun getLikes(postId: String) {
        postRepository.getLikes(postId)
    }



    val deletePostGetLiveData get() = postRepository.deletePostGetLiveData
    val addLikeGetLiveData get() = postRepository.addLikeGetLiveData
    val updatePostGetLiveData get() = postRepository.updatePostGetLiveData

    val getAllPostWhereUserIdGetLiveData get() = postRepository.getAllPostWhereUserIdGetLiveData
    val getAllPostsGetLiveData get() = postRepository.getAllPostsGetLiveData

    val getLikesGetLiveData get() = postRepository.getLikesGetLiveData
}