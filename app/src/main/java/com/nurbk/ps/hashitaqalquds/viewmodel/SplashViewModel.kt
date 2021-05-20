package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    application: Application
) :
    AndroidViewModel(application) {

    private val mutableLiveData = MutableLiveData<SplashState>()
    val liveData: LiveData<SplashState>
        get() = mutableLiveData
    private val mutableLiveDataZoom = MutableLiveData<SplashState>()
    val liveDataZoom: LiveData<SplashState>
        get() = mutableLiveDataZoom

    init {
        GlobalScope.launch {
            delay(4000)
            mutableLiveData.postValue(SplashState.SplashFragment)
        }

        GlobalScope.launch {
            delay(3900)
            mutableLiveDataZoom.postValue(SplashState.SplashFragment)
        }
    }

    sealed class SplashState {
        object SplashFragment : SplashState()
    }
}