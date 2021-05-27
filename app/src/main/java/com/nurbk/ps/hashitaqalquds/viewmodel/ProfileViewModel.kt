package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.hashitaqalquds.repository.PostRepository
import com.nurbk.ps.hashitaqalquds.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val userRepository: UserRepository, val postRepository: PostRepository,
    application: Application
) : AndroidViewModel(application) {
    fun update(id: String, user: Map<String, Any>) {
        userRepository.update(id, user){}
    }

    fun getWhereId(id: String) {
        userRepository.getWhereId(id) {
        }
    }

    fun uploadImage(
        oldPath: String,
        selectedImageBytes: ByteArray,
        onSuccess: (imagePath: String) -> Unit
    ) = userRepository.uploadImage(oldPath, selectedImageBytes, onSuccess)

    fun getAllPostWhereUserId(userId: String) =
        postRepository.getAllWhereUserId(userId)

    fun getPostLikeUser(userId:String){
        postRepository.getPostLikeUser(userId)
    }

    val getAllPostWhereUserIdGetLiveData get() = postRepository.getAllPostWhereUserIdGetLiveData
    val getWhereIdGetLiveData get() = userRepository.getWhereIdGetLiveData
    val updateGetLiveData get() = userRepository.updateGetLiveData
    val getPostLikesGetLiveData get() = postRepository.getPostLikesGetLiveData


}