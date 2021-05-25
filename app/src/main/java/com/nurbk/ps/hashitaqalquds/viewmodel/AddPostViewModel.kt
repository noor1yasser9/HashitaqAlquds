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
class AddPostViewModel @Inject constructor(
    val postRepository: PostRepository,
    application: Application
) : AndroidViewModel(application) {

    fun insert(post: Post) {
        postRepository.insert(post)
    }

    fun updatePost(postId: String, post: Map<String, Any>) {
        postRepository.update(postId, post)
    }


    //post getting live data
    val insertPostGetLiveData get() = postRepository.insertPostGetLiveData
    val updatePostGetLiveData get() = postRepository.insertPostGetLiveData

}