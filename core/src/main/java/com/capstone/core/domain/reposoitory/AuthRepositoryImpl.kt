package com.capstone.core.domain.reposoitory

import com.capstone.core.data.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepositoryImpl {

    fun login(request: LoginRequest): Flow<Resource<LoginResponse>>

}