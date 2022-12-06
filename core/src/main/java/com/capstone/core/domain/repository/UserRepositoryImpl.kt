package com.capstone.core.domain.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.request.update.UpdateRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface UserRepositoryImpl {
    fun fetch(): Flow<Resource<Wrapper<User>>>
    fun sendNotification(request: SendNotificationRequest): Flow<Any>
    fun updateUser(request: UpdateRequest): Flow<Resource<Wrapper<User>>>
}