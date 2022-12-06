package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.request.update.UpdateRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.data.source.UserDataSource
import com.capstone.core.domain.model.User
import com.capstone.core.domain.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(private val data: UserDataSource) : UserRepositoryImpl{

    override fun fetch(): Flow<Resource<Wrapper<User>>> = data.fetch()
    override fun sendNotification(request: SendNotificationRequest): Flow<Any> =
        data.sendNotification(request)
    override fun updateUser(request: UpdateRequest): Flow<Resource<Wrapper<User>>> =
        data.updateUser(request)
}