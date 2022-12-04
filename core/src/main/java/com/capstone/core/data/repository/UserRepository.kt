package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.data.source.UserDataSource
import com.capstone.core.domain.model.User
import com.capstone.core.domain.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val data: UserDataSource) : UserRepositoryImpl{

    override fun fetch(): Flow<Resource<Wrapper<User>>> = data.fetch()
    override fun sendNotification(request: SendNotificationRequest): Flow<Any> =
        data.sendNotification(request)
}