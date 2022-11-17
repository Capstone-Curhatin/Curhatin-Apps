package com.capstone.curhatin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.domain.usecase.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: StoryUseCase
) : ViewModel() {

    private fun createStory(request: StoryRequest): LiveData<Resource<GenericResponse>> =
        useCase.createStory(request).asLiveData()

}