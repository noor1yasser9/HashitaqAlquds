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


    private val insertPostLiveData: MutableLiveData<Result<Any?>> = MutableLiveData()
    private val updatePostLiveData: MutableLiveData<Result<Any?>> = MutableLiveData()

    fun insert(post: Post) {
        insertPostLiveData.postValue(Result.loading(""))
        postRepository.insert(post).addOnFailureListener {
            insertPostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            insertPostLiveData.postValue(Result.success(it))
        }
    }

    fun updatePost(postId: String, post: Map<String, Any>) {
        updatePostLiveData.postValue(Result.loading(""))
        postRepository.update(postId, post).addOnFailureListener {
            updatePostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            updatePostLiveData.postValue(Result.success(it))
        }
    }


    //post getting live data
    val insertPostGetLiveData get() = insertPostLiveData
    val updatePostGetLiveData get() = updatePostLiveData

}