package com.capstone.core.domain.usecase.auth

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.data.request.auth.VerifyOtpRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun login(request: LoginRequest): Flow<Resource<LoginResponse>>
    fun register(request: RegisterRequest): Flow<Resource<GenericResponse>>
    fun userVerification(request: VerifyOtpRequest): Flow<Resource<GenericResponse>>
    fun requestOtp(email: String): Flow<Resource<GenericResponse>>
    fun verifyOtp(request: VerifyOtpRequest): Flow<Resource<GenericResponse>>
    fun updateFcmToken(fcm: String): Flow<Resource<GenericResponse>>
    fun logout(): Flow<Resource<GenericResponse>>

}