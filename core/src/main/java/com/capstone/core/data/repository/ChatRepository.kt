package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.data.request.chat.ChatUserRequest
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.data.source.firebase.ChatStorage
import com.capstone.core.domain.repository.ChatRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepository @Inject constructor(private val data: ChatStorage) : ChatRepositoryImpl {

    override fun sendMessage(request: ChatRoomRequest): Flow<Resource<Boolean>> =
        data.sendMessage(request)

    override fun createChatGroup(request: ChatUserRequest): Flow<Resource<Boolean>> =
        data.createChatGroup(request)

    override fun getUserMessage(id: Int): Flow<Resource<List<ChatUserResponse>>> =
        data.getUserMessage(id)
}