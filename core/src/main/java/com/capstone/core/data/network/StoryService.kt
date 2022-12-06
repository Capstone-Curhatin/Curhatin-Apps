package com.capstone.core.data.network

import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.data.response.wrapper.WrapperPaginate
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Story
import com.capstone.core.utils.Endpoints
import retrofit2.Response
import retrofit2.http.*

interface StoryService {

    @POST(Endpoints.CREATE_STORY)
    suspend fun createStory(
        @Body request: StoryRequest
    ): Response<GenericResponse>

    @GET(Endpoints.GET_STORY)
    suspend fun getStory(
        @Query("page") page: Int
    ): WrapperPaginate<Story>

    @GET(Endpoints.GET_CATEGORY)
    suspend fun getCategory(): Response<WrapperList<Category>>

    @GET(Endpoints.GET_STORY_BY_USER)
    suspend fun getStoryByUser(
        @Query("page") page: Int
    ): WrapperPaginate<Story>

    @GET(Endpoints.GET_STORY_BY_CATEGORY)
    suspend fun getStoryByCategory(
        @Query("page") page: Int,
        @Path("id") id: Int
    ): WrapperPaginate<Story>

    @GET(Endpoints.INCREMENT_COMMENTS)
    suspend fun increment(
        @Path("id") id: Int
    ): Response<Any>

    @GET(Endpoints.DECREMENT_COMMENTS)
    suspend fun decrement(
        @Path("id") id: Int
    ): Response<Any>

}