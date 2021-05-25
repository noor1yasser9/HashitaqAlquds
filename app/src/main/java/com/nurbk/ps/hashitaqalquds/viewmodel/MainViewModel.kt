package com.nurbk.ps.hashitaqalquds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.Result
import com.nurbk.ps.hashitaqalquds.repository.PostRepository
import com.nurbk.ps.hashitaqalquds.repository.UserRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository()
    private val postRepository = PostRepository()

    //post live data
    private val insertPostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val updatePostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val deletePostLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val addLikeLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val addCommentLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getAllPostWhereUserIdLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getLikesLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getCommentsLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getAllPostsLiveData: MutableLiveData<Result<Any>> = MutableLiveData()

    //user live data
    private val insertUserLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val updateUserLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val deleteUserLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val getUserWhereIdLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val signUpLiveData: MutableLiveData<Result<Any>> = MutableLiveData()
    private val signInLiveData: MutableLiveData<Result<Any>> = MutableLiveData()

    //post function
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

    fun deletePost(postId: String) {
        deletePostLiveData.postValue(Result.loading(""))
        postRepository.delete(postId).addOnFailureListener {
            deletePostLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            deletePostLiveData.postValue(Result.success(it))
        }

    }

    fun addLike(postId: String, userId: String) {
        addLikeLiveData.postValue(Result.loading(""))
        postRepository.addLike(postId, userId).addOnFailureListener {
            addLikeLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            addLikeLiveData.postValue(Result.success(it))
        }

    }

    fun addComment(postId: String, post: Post) {
        addCommentLiveData.postValue(Result.loading(""))
        postRepository.addComment(postId, post).addOnFailureListener {
            addCommentLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            addCommentLiveData.postValue(Result.success(it))
        }

    }

    fun getAllPosts() {
        getAllPostsLiveData.postValue(Result.loading(""))
        postRepository.getAllPosts().addOnFailureListener {
            getAllPostsLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            val array = arrayListOf<Post>()
            it.forEach { p -> array.add(p.toObject(Post::class.java)) }
            getAllPostsLiveData.postValue(Result.success(array))
        }

    }

    fun getAllPostWhereUserId(userId: String) {
        getAllPostWhereUserIdLiveData.postValue(Result.loading(""))
        postRepository.getAllWhereUserId(userId).addOnFailureListener {
            getAllPostWhereUserIdLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            val array = arrayListOf<Post>()
            it.forEach { p -> array.add(p.toObject(Post::class.java)) }
            getAllPostWhereUserIdLiveData.postValue(Result.success(array))
        }

    }

    fun getLikes(postId: String) {
        getLikesLiveData.postValue(Result.loading(""))
        postRepository.getLikes(postId).addOnFailureListener {
            getLikesLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            // val array = arrayListOf<Post>()
            //it.forEach { p-> array.add(p.toObject(Post::class.java)) }
            //   getLikesLiveData.postValue(Result.success(array))
        }

    }

    fun getComments(postId: String) {
        getCommentsLiveData.postValue(Result.loading(""))
        postRepository.getComments(postId).addOnFailureListener {
            getCommentsLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            val array = arrayListOf<Post>()
            it.forEach { p -> array.add(p.toObject(Post::class.java)) }
            getCommentsLiveData.postValue(Result.success(array))
        }

    }

    // user functions
    fun insert(user: User) {
        insertUserLiveData.postValue(Result.loading(""))
        userRepository.insert(user).addOnFailureListener {
            insertUserLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            insertUserLiveData.postValue(Result.success(it))
        }

    }

    fun updateUser(userId: String, user: Map<String, Any>) {
        updateUserLiveData.postValue(Result.loading(""))
        userRepository.update(userId, user).addOnFailureListener {
            updateUserLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            updateUserLiveData.postValue(Result.success(it))
        }
    }

    fun deleteUser(userId: String) {
        deleteUserLiveData.postValue(Result.loading(""))
        userRepository.delete(userId).addOnFailureListener {
            deleteUserLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            deleteUserLiveData.postValue(Result.success(it))
        }

    }

    fun getUserWhereId(userId: String) {
        getUserWhereIdLiveData.postValue(Result.loading(""))
        userRepository.getWhereId(userId).addOnFailureListener {
            getUserWhereIdLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            getUserWhereIdLiveData.postValue(Result.success(it.toObject(Post::class.java)!!))
        }

    }

    fun signUp(email: String, pass: String, user: User) {
        signUpLiveData.postValue(Result.loading(""))
        userRepository.signUp(email, pass).addOnFailureListener {
            signUpLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            insert(user)
            signUpLiveData.postValue(Result.success(""))
        }

    }

    fun signIn(email: String, pass: String) {
        signInLiveData.postValue(Result.loading(""))
        userRepository.signIn(email, pass).addOnFailureListener {
            signInLiveData.postValue(Result.error(it.message, ""))
        }.addOnSuccessListener {
            signInLiveData.postValue(Result.success(""))
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

    //user getting live data
    val insertUserGetLiveData get() = insertUserLiveData
    val updateUserGetLiveData get() = updateUserLiveData
    val deleteUserGetLiveData get() = deleteUserLiveData
    val getUserWhereIdGetLiveData get() = getUserWhereIdLiveData
    val signUpGetLiveData get() = signUpLiveData
    val signInGetLiveData get() = signInLiveData

}