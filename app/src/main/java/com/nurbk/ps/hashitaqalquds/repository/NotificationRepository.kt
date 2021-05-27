package com.nurbk.ps.hashitaqalquds.repository

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

        CoroutineScope(Dispatchers.IO).launch {
            notificationMutableLiveData.postValue(Result.loading("loading"))
            val response = notificationInterface.sendRemoteMessage(notification)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            notificationMutableLiveData.postValue(Result.success(it))
                        }

                    } else {
                        notificationMutableLiveData.postValue(Result.success("Ooops: ${response.errorBody()}"))
                    }
                } catch (e: HttpException) {
                    notificationMutableLiveData.postValue(Result.success("Ooops: ${e.message()}"))

                } catch (t: Throwable) {
                    notificationMutableLiveData.postValue(Result.success("Ooops: ${t.message}"))
                }
            }
        }
    }


    val notificationPostGetLiveData get() = notificationMutableLiveData

}