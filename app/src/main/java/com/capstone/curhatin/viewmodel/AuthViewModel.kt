package com.capstone.curhatin.viewmodel

import androidx.lifecycle.*
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.FcmRequest
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.request.auth.PasswordRequest
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.data.request.auth.VerifyOtpRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.auth.LoginResponse
import com.capstone.core.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: AuthUseCase
) : ViewModel() {

    fun login(request: LoginRequest): LiveData<Resource<LoginResponse>> =
        useCase.login(request).asLiveData()

    fun register(request: RegisterRequest): LiveData<Resource<GenericResponse>> =
        useCase.register(request).asLiveData()

    fun userVerification(request: VerifyOtpRequest): LiveData<Resource<GenericResponse>> =
        useCase.userVerification(request).asLiveData()

    fun requestOtp(email: String): LiveData<Resource<GenericResponse>> =
        useCase.requestOtp(email).asLiveData()

    fun updatePassword(request: PasswordRequest): LiveData<Resource<GenericResponse>> =
        useCase.updatePassword(request).asLiveData()
}