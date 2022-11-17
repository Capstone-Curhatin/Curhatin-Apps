package com.capstone.core.data.network

import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.WrapperPaginateResponse
import com.capstone.core.domain.model.Story
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StoryService {

    @POST("createStory")
    suspend fun createStory(
        @Body request: StoryRequest
    ): Response<GenericResponse>

    @GET("getAllStory")
    suspend fun getStory(
        @Query("page") page: Int
    ): WrapperPaginateResponse<Story>

}