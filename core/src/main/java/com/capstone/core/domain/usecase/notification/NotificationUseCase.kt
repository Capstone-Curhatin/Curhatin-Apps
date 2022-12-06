package com.capstone.core.domain.usecase.notification

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.notification.CreateNotificationRequest
import com.capstone.core.data.response.NotificationResponse
import kotlinx.coroutines.flow.Flow

interface NotificationUseCase {

    fun createNotification(request: CreateNotificationRequest): Flow<Resource<Boolean>>
    fun getNotification(id: String): Flow<Resource<List<NotificationResponse>>>
    fun countNotification(id: String): Flow<Resource<Int>>

}