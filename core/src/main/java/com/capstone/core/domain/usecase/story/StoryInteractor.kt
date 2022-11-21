package com.capstone.core.domain.usecase.story

import androidx.paging.PagingData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Story
import com.capstone.core.domain.repository.StoryRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryInteractor @Inject constructor(
    private val repo: StoryRepositoryImpl
) : StoryUseCase {

    override fun createStory(request: StoryRequest): Flow<Resource<GenericResponse>> =
        repo.createStory(request)

    override fun getStory(): Flow<PagingData<Story>> =
        repo.getStory()

    override fun getCategory(): Flow<Resource<WrapperList<Category>>> =
        repo.getCategory()

}