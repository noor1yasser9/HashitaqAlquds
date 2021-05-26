package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.hashitaqalquds.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val userRepository: UserRepository,
    application: Application
) : AndroidViewModel(application) {
    fun update(id: String, user: Map<String, Any>) {
        userRepository.update(id,user)
    }
    fun getWhereId(id: String){
        userRepository.getWhereId(id){
        }
    }
    fun uploadImage(oldPath:String,
        selectedImageBytes: ByteArray,
        onSuccess: (imagePath: String) -> Unit
    ) = userRepository.uploadImage(oldPath,selectedImageBytes, onSuccess)


    val getWhereIdGetLiveData get() = userRepository.getWhereIdGetLiveData
    val updateGetLiveData get() = userRepository.updateGetLiveData

}