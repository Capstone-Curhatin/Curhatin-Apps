package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.domain.usecase.waiting.WaitingRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WaitingRoomViewModel @Inject constructor(
    private val useCase: WaitingRoomUseCase
) : ViewModel() {

    fun createWaitingRoom(request: WaitingRoomRequest): LiveData<Resource<GenericResponse>> =
        useCase.createRoom(request).asLiveData()

    fun updateWaitingRoom(request: WaitingRoomRequest) =
        useCase.updateStatus(request).asLiveData()


}