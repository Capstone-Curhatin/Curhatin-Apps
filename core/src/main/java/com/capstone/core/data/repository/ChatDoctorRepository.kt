package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.data.source.firebase.ChatDoctorStorage
import com.capstone.core.domain.repository.ChatDoctorRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatDoctorRepository @Inject constructor(private val data: ChatDoctorStorage) : ChatDoctorRepositoryImpl {

    override fun getUserMessage(id: String): Flow<Resource<List<ChatUserResponse>>> =
        data.getUserMessage(id)

    override fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>> =
        data.sendMessage(request)

    override fun readMessage(request: ReadMessageRequest): Flow<Resource<List<ChatRoomResponse>>> =
        data.readMessage(request)
}