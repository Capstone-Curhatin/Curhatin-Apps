package com.capstone.core.domain.usecase.comment

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.comment.CreateCommentRequest
import com.capstone.core.domain.model.Comment
import com.capstone.core.domain.repository.CommentRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentInteractor @Inject constructor(private val repo: CommentRepositoryImpl) : CommentUseCase {

    override fun createComment(request: CreateCommentRequest): Flow<Resource<Boolean>> =
        repo.createComment(request)

    override fun getComments(id: String): Flow<Resource<List<Comment>>> =
        repo.getComments(id)
}