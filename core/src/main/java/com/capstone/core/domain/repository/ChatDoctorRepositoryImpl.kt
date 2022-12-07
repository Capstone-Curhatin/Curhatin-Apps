package com.capstone.core.domain.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.data.response.chat.ChatUserResponse
import kotlinx.coroutines.flow.Flow

interface ChatDoctorRepositoryImpl {

    fun getUserMessage(id: String): Flow<Resource<List<ChatUserResponse>>>
    fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>>
    fun readMessage(request: ReadMessageRequest): Flow<Resource<List<ChatRoomResponse>>>

}