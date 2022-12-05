package com.capstone.core.data.source.firebase

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.comment.CreateCommentRequest
import com.capstone.core.domain.model.Comment
import kotlinx.coroutines.flow.Flow

interface CommentStorage {

    fun createComment(request: CreateCommentRequest): Flow<Resource<Boolean>>
    fun getComments(id: String): Flow<Resource<List<Comment>>>
}