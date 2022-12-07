package com.capstone.core.domain.usecase.chatDoctor

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.domain.repository.ChatDoctorRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatDoctorInteractor @Inject constructor(private val repo: ChatDoctorRepositoryImpl) : ChatDoctorUseCase {

    override fun getUserMessage(id: String): Flow<Resource<List<ChatUserResponse>>> =
        repo.getUserMessage(id)

    override fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>> =
        repo.sendMessage(request)

    override fun readMessage(request: ReadMessageRequest): Flow<Resource<List<ChatRoomResponse>>> =
        repo.readMessage(request)

}