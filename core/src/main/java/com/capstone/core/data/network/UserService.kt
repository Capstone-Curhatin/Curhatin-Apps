package com.capstone.core.data.network

import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import com.capstone.core.utils.Endpoints
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

    @POST(Endpoints.NOTIFICATION)
    suspend fun sendNotification(
        @Body request: SendNotificationRequest
    ): Response<Any>

}