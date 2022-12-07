package com.capstone.core.domain.usecase.chatDoctor

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import kotlinx.coroutines.flow.Flow

interface ChatDoctorUseCase {
    fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>>
}