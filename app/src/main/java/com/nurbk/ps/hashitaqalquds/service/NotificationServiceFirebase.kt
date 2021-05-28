package com.nurbk.ps.hashitaqalquds.service

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.nurbk.ps.hashitaqalquds.model.NotificationData
import com.nurbk.ps.hashitaqalquds.model.Post
import com.nurbk.ps.hashitaqalquds.model.User
import com.nurbk.ps.hashitaqalquds.other.COLLECTION_USERS
import com.nurbk.ps.hashitaqalquds.util.NotificationUtils

class NotificationServiceFirebase : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "Token: $token")
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        val data = remoteMessage.data
        Log.e("tttttttttt", data.toString())
        NotificationUtils.createMainNotificationChannel(this)
        try {
            val post: Post =
                Gson().fromJson(data["post"], Post::class.java)
            val comment: Post =
                Gson().fromJson(data["comment"], Post::class.java)
            getUser(
                comment.userId,
                comment.content,
                post.id
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun getUser(id: String, message: String, idN: String) {
        FirebaseFirestore.getInstance()
            .collection(COLLECTION_USERS)
            .document(id)
            .get()
            .addOnSuccessListener { command: DocumentSnapshot ->
                val users = command.toObject(User::class.java)
                NotificationUtils.showBasicNotification(
                    this,
                    users?.name,
                    message,
                    users?.image,
                    idN
                )
                Log.e("ttttttttttttt", users.toString())
            }.addOnFailureListener { e: Exception? -> }
    }

}