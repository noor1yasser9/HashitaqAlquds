package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.repository.PostRepository
import com.nurbk.ps.hashitaqalquds.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.nurbk.ps.hashitaqalquds.util.Result
import java.lang.Exception

@HiltViewModel
class AddPostViewModel @Inject constructor(
    val postRepository: PostRepository,
    val userRepository: UserRepository,
    application: Application
) : AndroidViewModel(application) {

    fun insert(post: Post) {
        postRepository.insert(post)
    }

    fun updatePost(postId: String, post: Map<String, Any>) {
        postRepository.update(postId, post) {}
    }


    //post getting live data
    val insertPostGetLiveData get() = postRepository.insertPostGetLiveData
    val updatePostGetLiveData get() = postRepository.insertPostGetLiveData
    fun getToken() = userRepository.getTokenId(){}

    init {
        getToken()
    }
    fun uploadImage(
        selectedImageBytes: ByteArray,
        onSuccess: (imagePath: String) -> Unit
    ) = postRepository.uploadImage(selectedImageBytes, onSuccess)


    fun uploadVideo(
        uri: Uri, type: String,
        onSuccess: (videoPath: String) -> Unit, onFailure: (expception: Exception) -> Unit
    ) = postRepository.uploadVideo(uri, type, onSuccess, onFailure)


}