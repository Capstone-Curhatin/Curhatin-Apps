package com.capstone.core.domain.usecase.user

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import com.capstone.core.domain.repository.UserRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val repo: UserRepositoryImpl
) : UserUseCase {

    override fun fetch(): Flow<Resource<Wrapper<User>>> =
        repo.fetch()

    override fun sendNotification(request: SendNotificationRequest): Flow<Any> =
        repo.sendNotification(request)
}