package com.capstone.core.data.network

import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.data.request.auth.VerifyOtpRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<GenericResponse>

    @POST("user_verification")
    suspend fun userVerification(
        @Body request: VerifyOtpRequest
    ): Response<GenericResponse>

    @FormUrlEncoded
    @GET("request_otp")
    suspend fun requestOtp(
        @Field("email") email: String
    ): Response<GenericResponse>

    @POST("verify_otp")
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<GenericResponse>

    @FormUrlEncoded
    @POST("update_fcm")
    suspend fun updateFcmToken(
        @Field("fcm") fcm: String
    ): Response<GenericResponse>
}