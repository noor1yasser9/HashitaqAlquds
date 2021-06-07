package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.nurbk.ps.hashitaqalquds.repository.PostRepository
import com.nurbk.ps.hashitaqalquds.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    val userRepository: UserRepository, val postRepository: PostRepository,
    application: Application
) : AndroidViewModel(application) {


       val getWhereIdGetLiveData get() = userRepository.getWhereIdGetLiveData
    val getPostLikesGetLiveData get() = postRepository.getPostLikesGetLiveData



}