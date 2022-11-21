package com.capstone.core.domain.usecase.auth

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.data.request.auth.VerifyOtpRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.auth.LoginResponse
import com.capstone.core.domain.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val repo: AuthRepositoryImpl
) : AuthUseCase {

    override fun login(request: LoginRequest): Flow<Resource<LoginResponse>> =
        repo.login(request)

    override fun register(request: RegisterRequest): Flow<Resource<GenericResponse>> =
        repo.register(request)

    override fun userVerification(request: VerifyOtpRequest): Flow<Resource<GenericResponse>> =
        repo.userVerification(request)

    override fun requestOtp(email: String): Flow<Resource<GenericResponse>> =
        repo.requestOtp(email)

    override fun verifyOtp(request: VerifyOtpRequest): Flow<Resource<GenericResponse>> =
        repo.verifyOtp(request)

    override fun updateFcmToken(fcm: String): Flow<Resource<GenericResponse>> =
        repo.updateFcmToken(fcm)
}