package com.capstone.core.domain.usecase.notification

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.notification.CreateNotificationRequest
import com.capstone.core.data.response.NotificationResponse
import com.capstone.core.domain.repository.NotificationRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationInteractor @Inject constructor(private val repo: NotificationRepositoryImpl) : NotificationUseCase {

    override fun createNotification(request: CreateNotificationRequest): Flow<Resource<Boolean>> =
        repo.createNotification(request)

    override fun getNotification(id: String): Flow<Resource<List<NotificationResponse>>> =
        repo.getNotification(id)

    override fun countNotification(id: String): Flow<Resource<Int>> =
        repo.countNotification(id)

}