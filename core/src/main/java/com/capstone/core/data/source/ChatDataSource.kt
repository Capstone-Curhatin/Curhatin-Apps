package com.capstone.core.data.source

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.ChatRoomRequest
import com.capstone.core.data.source.firebase.ChatStorage
import com.capstone.core.utils.Endpoints
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ChatDataSource : ChatStorage {

    override fun sendMessage(request: ChatRoomRequest): Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading())

        request.id = db.push().key.toString()

        db.child(request.sender_id.toString() + "/chat-room/" + request.id).setValue(request.toMap())
            .addOnSuccessListener {
                trySend(Resource.Success(true))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.printStackTrace().toString()))
            }
    }

    companion object {
        val db = FirebaseDatabase.getInstance().getReference(Endpoints.CHAT_ROOM)
    }

}