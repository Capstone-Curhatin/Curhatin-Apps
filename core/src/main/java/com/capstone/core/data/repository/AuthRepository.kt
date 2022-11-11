package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.auth.LoginResponse
import com.capstone.core.data.source.remote.AuthDataSource
import com.capstone.core.domain.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val data: AuthDataSource
) : AuthRepositoryImpl {

    override fun login(request: LoginRequest): Flow<Resource<LoginResponse>> =
        data.login(request)

    override fun register(request: RegisterRequest): Flow<Resource<GenericResponse>> =
        data.register(request)
}