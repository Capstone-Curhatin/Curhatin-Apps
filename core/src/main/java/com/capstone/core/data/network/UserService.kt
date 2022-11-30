package com.capstone.core.data.network

import com.capstone.core.data.request.auth.FcmRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import com.capstone.core.utils.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {

    @GET(Endpoints.FETCH_USER)
    suspend fun fetch(): Response<Wrapper<User>>

    @FormUrlEncoded
    @POST(Endpoints.UPDATE_FCM)
    suspend fun updateFcmToken(
        @Field("fcm") fcm: String
    ): Response<GenericResponse>

}