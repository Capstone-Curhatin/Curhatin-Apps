package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.usecase.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val useCase: StoryUseCase) : ViewModel() {

    fun getCategory(): LiveData<Resource<WrapperList<Category>>> =
        useCase.getCategory().asLiveData()

}