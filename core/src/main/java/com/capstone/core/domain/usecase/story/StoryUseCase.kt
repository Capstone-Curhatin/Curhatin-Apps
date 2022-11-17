package com.capstone.core.domain.usecase.story

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {
    fun createStory(request: StoryRequest): Flow<Resource<GenericResponse>>
}