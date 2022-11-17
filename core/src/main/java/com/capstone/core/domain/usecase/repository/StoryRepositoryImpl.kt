package com.capstone.core.domain.usecase.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import kotlinx.coroutines.flow.Flow

interface StoryRepositoryImpl {

    fun createStory(request: StoryRequest): Flow<Resource<GenericResponse>>

}