package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.comment.CreateCommentRequest
import com.capstone.core.data.source.firebase.CommentStorage
import com.capstone.core.domain.model.Comment
import com.capstone.core.domain.repository.CommentRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentRepository @Inject constructor(private val data: CommentStorage) : CommentRepositoryImpl {

    override fun createComment(request: CreateCommentRequest): Flow<Resource<Boolean>> =
        data.createComment(request)

    override fun getComments(id: String): Flow<Resource<List<Comment>>> =
        data.getComments(id)
}