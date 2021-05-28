package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.nurbk.ps.hashitaqalquds.repository.LandmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val landmarkRepository: LandmarkRepository,
    application: Application
) : AndroidViewModel(application) {
    fun getLandmark() = landmarkRepository.getLandmark()
    val getLandmarkLiveDataGetLiveData get() = landmarkRepository.getLandmarkLiveDataGetLiveData

}