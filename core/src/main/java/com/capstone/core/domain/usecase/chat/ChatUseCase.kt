package com.capstone.core.domain.usecase.chat

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.data.request.chat.ChatUserRequest
import com.capstone.core.data.response.chat.ChatUserResponse
import kotlinx.coroutines.flow.Flow

interface ChatUseCase {

    fun sendMessage(request: ChatRoomRequest): Flow<Resource<Boolean>>

    fun createChatGroup(request: ChatUserRequest): Flow<Resource<Boolean>>
    fun getUserMessage(id: Int): Flow<Resource<List<ChatUserResponse>>>

}