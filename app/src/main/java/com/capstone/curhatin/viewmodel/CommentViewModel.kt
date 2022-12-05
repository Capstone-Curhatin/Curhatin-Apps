package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.comment.CreateCommentRequest
import com.capstone.core.domain.model.Comment
import com.capstone.core.domain.usecase.comment.CommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(private val useCase: CommentUseCase) : ViewModel() {

    fun createViewModel(request: CreateCommentRequest){
        viewModelScope.launch(Dispatchers.IO){
            useCase.createComment(request).collectLatest {
                Timber.d("$it")
            }
        }
    }

    fun getComments(id: String): LiveData<Resource<List<Comment>>> =
        useCase.getComments(id).asLiveData()

}