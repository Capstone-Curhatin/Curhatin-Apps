package com.capstone.core.data.network

import com.capstone.core.data.request.auth.FcmRequest
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.request.auth.PasswordRequest
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.data.request.auth.VerifyOtpRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.auth.LoginResponse
import com.capstone.core.utils.Endpoints
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST(Endpoints.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST(Endpoints.REGISTER)
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<GenericResponse>

    @POST(Endpoints.USER_VERIFICATION)
    suspend fun userVerification(
        @Body request: VerifyOtpRequest
    ): Response<GenericResponse>

    @GET(Endpoints.REQUEST_OTP)
    suspend fun requestOtp(
       @Query("email") email: String
    ): Response<GenericResponse>

    @POST(Endpoints.VERIFY_OTP)
    suspend fun verifyOtp(
        @Body request: VerifyOtpRequest
    ): Response<GenericResponse>

    @POST(Endpoints.UPDATE_FCM)
    suspend fun updateFcmToken(
        @Body request: FcmRequest
    ): Response<GenericResponse>

    @POST(Endpoints.UPDATE_PASSWORD)
    suspend fun updatePassword(
        @Body request: PasswordRequest
    ): Response<GenericResponse>
}