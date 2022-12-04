package com.capstone.core.domain.usecase.user

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun fetch(): Flow<Resource<Wrapper<User>>>
    fun sendNotification(request: SendNotificationRequest): Flow<Any>
}