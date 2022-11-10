package com.capstone.core.data.source.remote

import com.capstone.core.data.base.ErrorParser
import com.capstone.core.data.base.MyDispatchers
import com.capstone.core.data.base.Resource
import com.capstone.core.data.base.SafeCall
import com.capstone.core.data.network.AuthService
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val safeCall: SafeCall,
    private val errorParser: ErrorParser,
    private val service: AuthService,
    private val dispatchers: MyDispatchers
) {

    fun login(request: LoginRequest): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(request, errorParser::converterGenericError, service::login)
        emit(res)
    }.flowOn(dispatchers.io)

}