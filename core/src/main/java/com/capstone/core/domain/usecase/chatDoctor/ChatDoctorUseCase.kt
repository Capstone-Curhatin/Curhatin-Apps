package com.capstone.core.domain.usecase.chatDoctor

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.data.response.chat.ChatUserResponse
import kotlinx.coroutines.flow.Flow

interface ChatDoctorUseCase {
    fun getUserMessage(id: String): Flow<Resource<List<ChatUserResponse>>>
    fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>>
}