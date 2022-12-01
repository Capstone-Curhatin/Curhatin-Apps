package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.data.request.chat.ChatUserRequest
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.domain.usecase.chat.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val useCase: ChatUseCase) : ViewModel() {

    fun sendMessage(request: ChatRoomRequest): LiveData<Resource<Boolean>> =
        useCase.sendMessage(request).asLiveData()

    fun createChatGroup(request: ChatUserRequest): LiveData<Resource<Boolean>> =
        useCase.createChatGroup(request).asLiveData()

    fun getUserMessage(id: Int): LiveData<Resource<List<ChatUserResponse>>> =
        useCase.getUserMessage(id).asLiveData()
}