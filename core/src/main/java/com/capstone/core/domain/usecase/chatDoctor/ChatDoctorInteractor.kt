package com.capstone.core.domain.usecase.chatDoctor

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.domain.repository.ChatDoctorRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatDoctorInteractor @Inject constructor(private val repo: ChatDoctorRepositoryImpl) : ChatDoctorUseCase {

    override fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>> =
        repo.sendMessage(request)

}