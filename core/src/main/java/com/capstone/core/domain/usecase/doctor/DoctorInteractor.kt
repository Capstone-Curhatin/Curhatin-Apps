package com.capstone.core.domain.usecase.doctor

import androidx.paging.PagingData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Doctor
import com.capstone.core.domain.model.Story
import com.capstone.core.domain.model.User
import com.capstone.core.domain.repository.DoctorRepositoryImpl
import com.capstone.core.domain.repository.StoryRepositoryImpl
import com.capstone.core.domain.usecase.story.StoryUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoctorInteractor @Inject constructor(
    private val repo: DoctorRepositoryImpl
) : DoctorUseCase {

    override fun getDoctor(): Flow<Resource<WrapperList<User>>> =
        repo.getDoctor()

    override fun detailDoctor(id : Int): Flow<Resource<Wrapper<User>>> =
        repo.detailDoctor(id)
}