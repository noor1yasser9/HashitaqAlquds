package com.nurbk.ps.hashitaqalquds.network

import com.nurbk.ps.hashitaqalquds.model.NotificationParent
import com.nurbk.ps.hashitaqalquds.other.AUTH_VALUE
import com.nurbk.ps.hashitaqalquds.other.VALUE_TYPE
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationInterface {

    @Headers(
        "Authorization: key=${AUTH_VALUE}",
        "Content-Type:${VALUE_TYPE}"
    )
    @POST("send")

    suspend fun sendRemoteMessage(
        @Body notification: NotificationParent
    ): Response<ResponseBody>

}