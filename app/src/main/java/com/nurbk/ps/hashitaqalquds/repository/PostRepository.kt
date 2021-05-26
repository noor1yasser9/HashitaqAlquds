package com.nurbk.ps.hashitaqalquds.repository

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.COLLECTION_POST
import com.nurbk.ps.hashitaqalquds.other.COLLECTION_USERS
import com.nurbk.ps.hashitaqalquds.util.Result
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class PostRepository @Inject constructor() {


    private val insertPostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val updatePostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val deletePostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val addLikeLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val addCommentLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getAllPostWhereUserIdLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getAllPostsLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getLikesLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getCommentsLiveData: MutableLiveData<Result<Any>> = MutableLiveData()


    private val db by lazy {
        FirebaseFirestore.getInstance()
    }


    fun insert(post: Post) {
        insertPostLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(post.id).set(post).addOnFailureListener {
            insertPostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            insertPostLiveData.postValue(Result.success(""))
        }
    }


    fun update(postId: String, post: Map<String, Any>) {
        updatePostLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).update(post).addOnFailureListener {
            updatePostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            updatePostLiveData.postValue(Result.success(it))
        }
    }

    fun delete(postId: String) {
        deletePostLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).delete().addOnFailureListener {
            deletePostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            deletePostLiveData.postValue(Result.success(it))
        }
    }


    fun addLike(postId: String, userId: String) {
        addLikeLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).collection("likes").add(userId)
            .addOnFailureListener {
                addLikeLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                addLikeLiveData.postValue(Result.success(it))
            }

    }


    fun addComment(postId: String, post: Post) {
        addCommentLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).collection("comments").add(post)
            .addOnFailureListener {
                addCommentLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                addCommentLiveData.postValue(Result.success(it))
            }
    }

    fun getAllPosts() {
        getAllPostsLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).addSnapshotListener { value, error ->
            if (error == null) {
                val array = ArrayList<Post>()
                value?.let {
                    it.forEach { p ->
                        val post: Post =
                            p.toObject(Post::class.java)
                        FirebaseFirestore.getInstance()
                            .collection(COLLECTION_USERS)
                            .document(post.userId)
                            .addSnapshotListener { value1: DocumentSnapshot?, error1: FirebaseFirestoreException? ->
                                if (error1 == null) {
                                    if (value1 != null)
                                        post.users = (value1.toObject(User::class.java)!!)
                                    array.add(post)
                                    getAllPostsLiveData.postValue(Result.success(array))
                                }
                            }
                    }
                }
                if (value == null)
                    getAllPostsLiveData.postValue(Result.empty(array))
            } else {
                getAllPostsLiveData.postValue(Result.error(error.message, ""))
            }
        }
    }

    fun getAllWhereUserId(userId: String) {
        getAllPostWhereUserIdLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).whereEqualTo("userId", userId).get()
            .addOnFailureListener {
                getAllPostWhereUserIdLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                val array = arrayListOf<Post>()
                it.forEach { p -> array.add(p.toObject(Post::class.java)) }
                getAllPostWhereUserIdLiveData.postValue(Result.success(array))
            }
    }

    fun getLikes(postId: String) {
        getLikesLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).collection("likes").get()
            .addOnFailureListener {
                getLikesLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                // val array = arrayListOf<Post>()
                //it.forEach { p-> array.add(p.toObject(Post::class.java)) }
                //   getLikesLiveData.postValue(Result.success(array))
            }

    }

    fun getComments(postId: String) {
        getCommentsLiveData.postValue(Result.loading(""))
        db.collection(COLLECTION_POST).document(postId).collection("comments").get()
            .addOnFailureListener {
                getCommentsLiveData.postValue(Result.error(it.message, ""))
            }.addOnSuccessListener {
                val array = arrayListOf<Post>()
                it.forEach { p -> array.add(p.toObject(Post::class.java)) }
                getCommentsLiveData.postValue(Result.success(array))
            }
    }


    fun uploadImage(
        selectedImageBytes: ByteArray,
        onSuccess: (imagePath: String) -> Unit
    ) {
        val ref = FirebaseStorage.getInstance().reference
            .child(FirebaseAuth.getInstance().currentUser?.uid!!)
            .child(Calendar.getInstance().time.toString())
        ref.putBytes(selectedImageBytes)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    onSuccess(it.toString())
                }
            }.addOnFailureListener {

            }
    }


    fun uploadVideo(
        uri: Uri, type: String,
        onSuccess: (videoPath: String) -> Unit, onFailure: (expception: Exception) -> Unit
    ) {

        val firebaseStorage = FirebaseStorage.getInstance()
        val videoReference = firebaseStorage.reference.child("video")
        val uploadTask: UploadTask = videoReference
            .child(uri.lastPathSegment + ".$type")
            .putFile(uri)

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnFailureListener { exception ->
            onFailure(exception)
        }.addOnSuccessListener { success: UploadTask.TaskSnapshot ->
            val audioUrl =
                success.storage.downloadUrl
            audioUrl.addOnCompleteListener { path: Task<Uri> ->
                if (path.isSuccessful) {
                    onSuccess(path.result.toString())
                }
            }
        }
    }


    //post getting live data
    val insertPostGetLiveData get() = insertPostLiveData
    val updatePostGetLiveData get() = updatePostLiveData
    val deletePostGetLiveData get() = deletePostLiveData
    val addLikeGetLiveData get() = addLikeLiveData
    val addCommentGetLiveData get() = addCommentLiveData
    val getAllPostWhereUserIdGetLiveData get() = getAllPostWhereUserIdLiveData
    val getAllPostsGetLiveData get() = getAllPostsLiveData
    val getLikesGetLiveData get() = getLikesLiveData
    val getCommentsGetLiveData get() = getCommentsLiveData


    init {
        getAllPosts()
    }

}
