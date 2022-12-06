package com.capstone.core.data.source

import com.capstone.core.data.common.ErrorParser
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.Resource
import com.capstone.core.data.common.SafeCall
import com.capstone.core.data.network.UserService
import com.capstone.core.data.request.auth.FcmRequest
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.request.update.UpdateRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import com.capstone.core.utils.MySharedPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import timber.log.Timber
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val safeCall: SafeCall,
    private val dispatchers: MyDispatchers,
    private val converter: ErrorParser,
    private val service: UserService,
    private val prefs: MySharedPreference
) {

    fun fetch(): Flow<Resource<Wrapper<User>>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(converter::converterGenericError, service::fetch)
        if (prefs.getFcm() != res.data?.data?.fcm) {
            safeCall.enqueue(prefs.getFcm(), converter::converterGenericError, service::updateFcmToken)
            Timber.d("UPDATED FCM")
        }
        emit(res)
    }.flowOn(dispatchers.io)

    fun sendNotification(request: SendNotificationRequest) = flow<Any> {

        val res = safeCall.enqueue(request, converter::converterGenericError, service::sendNotification)
        emit(res)
    }.flowOn(dispatchers.io)


    fun updateUser(request: UpdateRequest) : Flow<Resource<Wrapper<User>>> = flow {
        emit(Resource.Loading())

        val data = if (request.picture == null) request.updateUserAnonym() else request.updateUser()
        val res = safeCall.enqueue(data, converter::converterGenericError, service::updateUser)
        emit(res)
    }.flowOn(dispatchers.io)
}