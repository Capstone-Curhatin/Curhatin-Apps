package com.capstone.core.data.network

import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.User
import com.capstone.core.utils.Endpoints
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface DoctorService {

    @GET(Endpoints.GET_DOCTOR)
    suspend fun getDoctor(): Response<WrapperList<User>>

    @GET(Endpoints.DETAIL_DOCTOR)
    suspend fun detailDoctor(
        @Path("id") id: Int
    ): Response<Wrapper<User>>
}