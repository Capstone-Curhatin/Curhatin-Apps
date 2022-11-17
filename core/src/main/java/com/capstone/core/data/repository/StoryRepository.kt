package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.source.StoryDataSource
import com.capstone.core.domain.usecase.repository.StoryRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryRepository @Inject constructor(
    private val data: StoryDataSource
) : StoryRepositoryImpl {

    override fun createStory(request: StoryRequest): Flow<Resource<GenericResponse>> =
        data.createStory(request)

}