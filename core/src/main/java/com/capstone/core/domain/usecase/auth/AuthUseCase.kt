package com.capstone.core.domain.usecase.auth

import com.capstone.core.data.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun login(request: LoginRequest): Flow<Resource<LoginResponse>>

}