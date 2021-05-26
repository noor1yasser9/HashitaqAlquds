package com.nurbk.ps.hashitaqalquds.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.COLLECTION_USERS
import com.nurbk.ps.hashitaqalquds.util.Result
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor() {

    private val TAG: String = UserRepository::class.java.name

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    fun update(id: String, user: Map<String, Any>) =
        db.collection(COLLECTION_USERS).document(id).update(user).addOnSuccessListener {
            updateLiveData.postValue(
                Result.success(it)
            )
        }.addOnFailureListener {
            updateLiveData.postValue(
                Result.error(it.message,null)
            )
        }

    private val signUpLiveData: MutableLiveData<Result<Any?>> = MutableLiveData()
    private val signInLiveData: MutableLiveData<Result<Any?>> = MutableLiveData()
    private val getWhereIdLiveData: MutableLiveData<Result<Any?>> = MutableLiveData()
    private val updateLiveData: MutableLiveData<Result<Any?>> = MutableLiveData()


    fun signIn(email: String, pass: String, onComplete: (String) -> Unit) {
        signInLiveData.postValue(Result.loading(null))
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener { su: AuthResult? -> getWhereId(auth.uid.toString(), onComplete) }
            .addOnFailureListener { ex: Exception? ->
                signInLiveData.postValue(Result.error("", ex))
            }
    }

    fun getWhereId(id: String, onComplete: (String) -> Unit) {
        getWhereIdLiveData.postValue(Result.loading(null))

        db.collection(COLLECTION_USERS)
            .document(id)
            .addSnapshotListener { query: DocumentSnapshot?, ex: FirebaseFirestoreException? ->
                if (ex == null) {
                    val users: User? = query!!.toObject(User::class.java)
                    val user = Gson().toJson(users)
                    onComplete(user)
                    getWhereIdLiveData.postValue(Result.success(users))
                    signInLiveData.postValue(Result.success(user))

                } else getWhereIdLiveData.postValue(Result.error("", ex))
            }
    }

    fun uploadImage(
        oldPath: String,
        selectedImageBytes: ByteArray,
        onSuccess: (imagePath: String) -> Unit
    ) {
        val ref = FirebaseStorage.getInstance().reference
            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
            .child(Calendar.getInstance().time.toString())
        ref.putBytes(selectedImageBytes)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    if (oldPath != "") deleteImage(oldPath)
                    onSuccess(it.toString())
                }
            }.addOnFailureListener {

            }
    }

    fun deleteImage(path: String) {
        val photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(path)
        photoRef.delete().addOnSuccessListener { // File deleted successfully
            Log.d(TAG, "onSuccess: deleted file")
        }.addOnFailureListener { // Uh-oh, an error occurred!
            Log.d(TAG, "onFailure: did not delete file")
        }

    }

    fun createAccount(users: User, password: String?) {
        signUpLiveData.postValue(Result.loading(null))
        auth.createUserWithEmailAndPassword(users.email, password!!)
            .addOnSuccessListener { su: AuthResult? ->
                users.id = (FirebaseAuth.getInstance().uid).toString()
                insert(users)
            }.addOnFailureListener { ex: java.lang.Exception? ->
//                handelExp(ex!!)
                Log.e("ttttttt", ex!!.message!!)
                signUpLiveData.postValue(Result.error("", ex))

            }
    }

    private fun insert(user: User) {
        db
            .collection(COLLECTION_USERS)
            .document(user.id)
            .set(user).addOnSuccessListener { su: Void? ->
                signUpLiveData.postValue(
                    Result.success(true)
                )
            }.addOnFailureListener { ex: java.lang.Exception? -> handelExp(ex!!) }
    }


    private fun handelExp(ex: java.lang.Exception) {
        try {
            throw ex
        } catch (e: FirebaseAuthUserCollisionException) {
            signUpLiveData.postValue(Result.error("", ex))
        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.message!!)
        }
    }

    val signUpGetLiveData get() = signUpLiveData
    val signInGetLiveData get() = signInLiveData
    val getWhereIdGetLiveData get() = getWhereIdLiveData
    val updateGetLiveData get() = updateLiveData


}