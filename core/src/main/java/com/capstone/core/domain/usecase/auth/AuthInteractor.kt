package com.capstone.core.domain.usecase.auth

import com.capstone.core.data.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.response.auth.LoginResponse
import com.capstone.core.domain.reposoitory.AuthRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val repo: AuthRepositoryImpl
) : AuthUseCase {

    override fun login(request: LoginRequest): Flow<Resource<LoginResponse>> =
        repo.login(request)
}