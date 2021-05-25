package com.nurbk.ps.hashitaqalquds.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nurbk.ps.hashitaqalquds.model.User

class UserRepository private constructor() {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }
    fun insert(user:User) = db.collection("users").add(user)
    fun update(id:String,user:Map<String,Any>) = db.collection("users").document(id).update(user)
    fun delete(id:String) = db.collection("users").document(id).delete()
    fun getWhereId(id:String) = db.collection("users").document(id).get()


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        private val LOCK = Any()

        operator fun invoke() =
            instance ?: synchronized(LOCK) {
                instance ?: createUserRepository().also {
                    instance = it
                }
            }

        private fun createUserRepository() = UserRepository()
    }
}