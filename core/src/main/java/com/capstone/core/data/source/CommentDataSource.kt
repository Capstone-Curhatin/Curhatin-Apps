package com.capstone.core.data.source

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.comment.CreateCommentRequest
import com.capstone.core.data.source.firebase.CommentStorage
import com.capstone.core.domain.model.Comment
import com.capstone.core.utils.Endpoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CommentDataSource : CommentStorage {

    override fun createComment(request: CreateCommentRequest): Flow<Resource<Boolean>> = callbackFlow {

        trySend(Resource.Loading())

        request.id = db.push().key
        db.child(request.id_story.toString()).child(request.id.toString()).setValue(request.toMap())
            .addOnSuccessListener { trySend(Resource.Success(true)) }
            .addOnFailureListener { trySend(Resource.Error(it.localizedMessage)) }

        awaitClose { this.close() }
    }

    override fun getComments(id: String): Flow<Resource<List<Comment>>> = callbackFlow {

        trySend(Resource.Loading())

        db.child(id).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Comment>()

                for (i in snapshot.children){
                    val data = i.getValue(Comment::class.java)
                    if (data != null) list.add(data)
                }

                // send response
                trySend(Resource.Success(list))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
            }
        })

        awaitClose { this.close() }
    }

    companion object {
        private val db = FirebaseDatabase.getInstance().getReference(Endpoints.COMMENT)
    }
}