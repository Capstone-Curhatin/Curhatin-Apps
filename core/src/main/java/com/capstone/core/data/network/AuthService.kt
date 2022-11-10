package com.capstone.core.data.network

import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.response.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

}