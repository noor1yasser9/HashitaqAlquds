package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    val postRepository: PostRepository,
    application: Application
) : AndroidViewModel(application) {


    fun addComment(postId: String, post: Post) {
        postRepository.addComment(postId, post)
    }

    fun getComments(postId: String) {
        postRepository.getComments(postId)
    }

    val addCommentGetLiveData get() = postRepository.addCommentGetLiveData
    val getCommentsGetLiveData get() = postRepository.getCommentsGetLiveData

}