package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.notification.CreateNotificationRequest
import com.capstone.core.data.response.NotificationResponse
import com.capstone.core.data.source.firebase.NotificationStorage
import com.capstone.core.domain.repository.NotificationRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val data: NotificationStorage) : NotificationRepositoryImpl {

    override fun createNotification(request: CreateNotificationRequest): Flow<Resource<Boolean>> =
        data.createNotification(request)

    override fun getNotification(id: String): Flow<Resource<List<NotificationResponse>>> =
        data.getNotification(id)

    override fun countNotification(id: String): Flow<Resource<Int>> =
        data.countNotification(id)

}