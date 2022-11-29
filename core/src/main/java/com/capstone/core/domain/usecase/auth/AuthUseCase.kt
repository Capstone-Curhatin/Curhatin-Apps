package com.capstone.core.domain.usecase.auth

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.FcmRequest
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.request.auth.PasswordRequest
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
<<<<<<< HEAD
    fun updateFcmToken(fcm: FcmRequest): Flow<Resource<GenericResponse>>
=======
    fun updateFcmToken(fcm: String): Flow<Resource<GenericResponse>>
    fun updatePassword(request: PasswordRequest): Flow<Resource<GenericResponse>>
>>>>>>> 1ea4c8a065481aa2db6494a6cec57ff2a95aef89

}