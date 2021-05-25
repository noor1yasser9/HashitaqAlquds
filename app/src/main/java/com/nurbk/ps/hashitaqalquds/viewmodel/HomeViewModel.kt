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
    fun addLike(postId: String, userId: String) {
        postRepository.addLike(postId, userId)
    }
    fun addComment(postId: String, post: Post) {
        postRepository.addComment(postId, post)
    }
    fun getAllPostWhereUserId(userId: String) {
        postRepository.getAllWhereUserId(userId)
    }
    fun getLikes(postId: String) {
        postRepository.getLikes(postId)
    }
    fun getComments(postId: String) {
        postRepository.getComments(postId)
    }


    val deletePostGetLiveData get() = postRepository.deletePostGetLiveData
    val addLikeGetLiveData get() = postRepository.addLikeGetLiveData
    val addCommentGetLiveData get() = postRepository.addCommentGetLiveData
    val updatePostGetLiveData get() = postRepository.updatePostGetLiveData

    val getAllPostWhereUserIdGetLiveData get() = postRepository.getAllPostWhereUserIdGetLiveData
    val getAllPostsGetLiveData get() = postRepository.getAllPostsGetLiveData

    val getLikesGetLiveData get() = postRepository.getLikesGetLiveData
    val getCommentsGetLiveData get() =  postRepository.getCommentsGetLiveData
}