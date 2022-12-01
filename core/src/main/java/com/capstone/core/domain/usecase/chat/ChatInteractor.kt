package com.capstone.core.domain.usecase.chat

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.data.request.chat.ChatUserRequest
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.domain.repository.ChatRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatInteractor @Inject constructor(private val repo: ChatRepositoryImpl) : ChatUseCase {

    override fun sendMessage(request: ChatRoomRequest): Flow<Resource<Boolean>> =
        repo.sendMessage(request)

    override fun readMessage(request: ReadMessageRequest): Flow<Resource<List<ChatRoomResponse>>> =
        repo.readMessage(request)

    override fun createChatGroup(request: ChatUserRequest): Flow<Resource<Boolean>> =
        repo.createChatGroup(request)

    override fun getUserMessage(id: Int): Flow<Resource<List<ChatUserResponse>>> =
        repo.getUserMessage(id)
}