package com.capstone.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.network.StoryService
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.data.source.StoryDataSource
import com.capstone.core.data.source.StoryPagingSource
import com.capstone.core.data.source.StoryUserPagingSource
import com.capstone.core.data.source.StoryCategoryPagingSource
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Story
import com.capstone.core.domain.repository.StoryRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryRepository @Inject constructor(
    private val data: StoryDataSource,
    private val service: StoryService
) : StoryRepositoryImpl {

    override fun createStory(request: StoryRequest): Flow<Resource<GenericResponse>> =
        data.createStory(request)

    override fun getStory(): Flow<PagingData<Story>> = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {StoryPagingSource(service)}
    ).flow

    override fun getCategory(): Flow<Resource<WrapperList<Category>>> =
        data.getCategory()

    override fun getStoryByUser(): Flow<PagingData<Story>> = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = {StoryUserPagingSource(service)}
    ).flow

    override fun getStoryByCategory(id: Int): Flow<PagingData<Story>> = Pager(
        config = PagingConfig(
            pageSize = 5,
            maxSize = 20,
            enablePlaceholders = false
        ), pagingSourceFactory = { StoryCategoryPagingSource(service,id) }
    ).flow
}