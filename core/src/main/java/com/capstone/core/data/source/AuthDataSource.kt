package com.capstone.core.data.source

import android.util.Log
import com.capstone.core.data.common.ErrorParser
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.Resource
import com.capstone.core.data.common.SafeCall
import com.capstone.core.data.network.AuthService
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.request.auth.RegisterRequest
import com.capstone.core.data.request.auth.VerifyOtpRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.auth.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
    this class for send request to API and receive response
    if you want create another class, write in the remote folder and room folder for local database
    and add this class to AppModule in the folder di
 */
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

    fun register(request: RegisterRequest): Flow<Resource<GenericResponse>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(request, errorParser::converterGenericError, service::register)
        emit(res)
    }.flowOn(dispatchers.io)

    fun userVerification(request: VerifyOtpRequest): Flow<Resource<GenericResponse>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(request, errorParser::converterGenericError, service::userVerification)
        emit(res)
    }.flowOn(dispatchers.io)

    fun requestOtp(email: String): Flow<Resource<GenericResponse>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(email, errorParser::converterGenericError, service::requestOtp)
        emit(res)
    }.flowOn(dispatchers.io)

    fun verifyOtp(request: VerifyOtpRequest): Flow<Resource<GenericResponse>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(request, errorParser::converterGenericError, service::verifyOtp)
        emit(res)
    }.flowOn(dispatchers.io)

    fun updateFcmToken(fcm: String): Flow<Resource<GenericResponse>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(fcm, errorParser::converterGenericError, service::updateFcmToken)
        emit(res)
    }.flowOn(dispatchers.io)

    fun logout(): Flow<Resource<GenericResponse>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(errorParser::converterGenericError, service::logout)
        emit(res)
    }.flowOn(dispatchers.io)

}