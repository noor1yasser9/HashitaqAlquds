package com.nurbk.ps.hashitaqalquds.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.nurbk.ps.hashitaqalquds.model.NotificationParent
import com.nurbk.ps.hashitaqalquds.network.NotificationInterface
import com.nurbk.ps.hashitaqalquds.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    val notificationInterface: NotificationInterface,
) {
    private val notificationMutableLiveData: MutableLiveData<Result<Any>> = MutableLiveData()


    fun sendRemoteMessage(notification: NotificationParent) {
        Log.e("ttttttttttttsdfsdf", notification.data.toString())
        CoroutineScope(Dispatchers.IO).launch {
            notificationMutableLiveData.postValue(Result.loading("loading"))
            val response = notificationInterface.sendRemoteMessage(notification)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            notificationMutableLiveData.postValue(Result.success(it))
                            Log.e("ttttttt", it.toString())
                        }

                    } else {
                        notificationMutableLiveData.postValue(Result.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    Log.e("ttttttt", e.message().toString())
                    notificationMutableLiveData.postValue(Result.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    Log.e("ttttttt", t.message.toString())
                    notificationMutableLiveData.postValue(Result.success("Ooops: ${t.message}"))
                }
            }
        }
    }


    val notificationPostGetLiveData get() = notificationMutableLiveData

}