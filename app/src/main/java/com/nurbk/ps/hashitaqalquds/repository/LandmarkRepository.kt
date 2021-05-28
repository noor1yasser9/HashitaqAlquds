package com.nurbk.ps.hashitaqalquds.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.nurbk.ps.hashitaqalquds.model.Landmark
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.other.COLLECTION_LANDMARK
import com.nurbk.ps.hashitaqalquds.other.COLLECTION_POST
import com.nurbk.ps.hashitaqalquds.util.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LandmarkRepository  @Inject constructor(){

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }
    private val getLandmarkLiveData: MutableLiveData<Result<Any>> = MutableLiveData()

    fun getLandmark(){
        getLandmarkLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_LANDMARK).get()
            .addOnFailureListener {
                getLandmarkLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                val array = arrayListOf<Landmark>()
                it.forEach { p -> array.add(p.toObject(Landmark::class.java)) }
              //  Log.e("mmmmmmmm",array.toString())
                getLandmarkLiveData.postValue(Result.success(array))
            }
    }
    val getLandmarkLiveDataGetLiveData get() = getLandmarkLiveData
init {
    getLandmark()
}
}