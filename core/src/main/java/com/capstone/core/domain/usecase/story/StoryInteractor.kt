package com.capstone.core.domain.usecase.story

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.domain.usecase.repository.StoryRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryInteractor @Inject constructor(
    private val repo: StoryRepositoryImpl
) : StoryUseCase {

    override fun createStory(request: StoryRequest): Flow<Resource<GenericResponse>> =
        repo.createStory(request)

}