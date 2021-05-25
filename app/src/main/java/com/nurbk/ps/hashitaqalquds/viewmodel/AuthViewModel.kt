package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.IS_LOGIN
import com.nurbk.ps.hashitaqalquds.other.USER_PROFILE
import com.nurbk.ps.hashitaqalquds.repository.UserRepository
import com.nurbk.ps.hashitaqalquds.util.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val userRepository: UserRepository,
    application: Application
) : AndroidViewModel(application) {
    val mContext = application

    fun signIn(email: String, pass: String) {
        userRepository.signIn(email, pass) {
            PreferencesManager(mContext).editor.putString(USER_PROFILE, it).apply()
            PreferencesManager(mContext).editor.putBoolean(IS_LOGIN, true).apply()
        }
    }

    fun createAccount(user: User, pass: String) {
        userRepository.createAccount(user, pass)
    }

    val signUpGetLiveData get() = userRepository.signUpGetLiveData
    val signInGetLiveData get() = userRepository.signInGetLiveData

}