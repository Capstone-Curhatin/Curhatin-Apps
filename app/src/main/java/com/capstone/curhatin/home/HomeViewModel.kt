package com.capstone.curhatin.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.domain.model.Story
import com.capstone.core.domain.usecase.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: StoryUseCase
) : ViewModel() {

    fun createStory(request: StoryRequest): LiveData<Resource<GenericResponse>> =
        useCase.createStory(request).asLiveData()

    fun getStories(): LiveData<PagingData<Story>> =
        useCase.getStory().distinctUntilChanged().cachedIn(viewModelScope).asLiveData()

}