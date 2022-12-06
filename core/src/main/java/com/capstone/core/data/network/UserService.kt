package com.capstone.core.data.network

import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import com.capstone.core.utils.Endpoints
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET(Endpoints.FETCH_USER)
    suspend fun fetch(): Response<Wrapper<User>>

    @FormUrlEncoded
    @POST(Endpoints.UPDATE_FCM)
    suspend fun updateFcmToken(
        @Field("fcm") fcm: String
    ): Response<GenericResponse>

    @POST(Endpoints.SEND_NOTIFICATION)
    suspend fun sendNotification(
        @Body request: SendNotificationRequest
    ): Response<Any>

    @POST(Endpoints.UPDATE)
    suspend fun updateUser(
        @Body body: RequestBody,
    ): Response<Wrapper<User>>
}