package com.capstone.curhatin.viewmodel

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryViewModel @Inject constructor(
    private val useCase: StoryUseCase
) : ViewModel() {

    fun createStory(request: StoryRequest): LiveData<Resource<GenericResponse>> =
        useCase.createStory(request).asLiveData()

    fun getStories(): LiveData<PagingData<Story>> =
        useCase.getStory().distinctUntilChanged().cachedIn(viewModelScope).asLiveData()

    fun getStoryByUser(): LiveData<PagingData<Story>> =
        useCase.getStoryByUser().distinctUntilChanged().cachedIn(viewModelScope).asLiveData()

    fun getStoryByCategory(id: Int): LiveData<PagingData<Story>> =
        useCase.getStoryByCategory(id).distinctUntilChanged().cachedIn(viewModelScope).asLiveData()

    fun increment(id: Int) {
        viewModelScope.launch(Dispatchers.IO){
            useCase.increment(id).collectLatest {}
        }
    }
}