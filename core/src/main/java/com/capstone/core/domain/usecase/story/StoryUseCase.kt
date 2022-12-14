package com.capstone.core.domain.usecase.story

import androidx.paging.PagingData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {
    fun createStory(request: StoryRequest): Flow<Resource<GenericResponse>>
    fun getStory(): Flow<PagingData<Story>>
    fun getCategory(): Flow<Resource<WrapperList<Category>>>
    fun getStoryByUser(): Flow<PagingData<Story>>
    fun getStoryByCategory(id: Int): Flow<PagingData<Story>>
    fun increment(id: Int): Flow<Resource<Any>>
}