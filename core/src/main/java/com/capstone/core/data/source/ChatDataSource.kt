package com.capstone.core.data.source

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.data.request.chat.ChatUserRequest
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.data.source.firebase.ChatStorage
import com.capstone.core.utils.Endpoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ChatDataSource : ChatStorage {

    override fun sendMessage(request: ChatRoomRequest): Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading())

        request.id = db.push().key.toString()

        // room chat for sender and update last chat
        db.child(request.sender_id.toString()).child(request.receiver_id.toString())
            .child(Endpoints.CHAT_ROOM).child(request.id.toString()).setValue(request.toMap())
            .addOnSuccessListener {

                // set last message
                val reqLast = ChatUserRequest(last_date = request.date, last_message = request.message)
                db.child(request.sender_id.toString()).child(request.receiver_id.toString()).updateChildren(reqLast.toUpdateLastMessage())

                // room chat from receiver and update last chat
                db.child(request.receiver_id.toString()).child(request.sender_id.toString())
                    .child(Endpoints.CHAT_ROOM).child(request.id.toString()).setValue(request.toMap())
                    .addOnSuccessListener {

                        // update last message
                        db.child(request.receiver_id.toString()).child(request.sender_id.toString()).updateChildren(reqLast.toUpdateLastMessage())

                        // send response
                        trySend(Resource.Success(true))
                    }
                    .addOnFailureListener {
                        trySend(Resource.Error(it.printStackTrace().toString()))
                    }
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.printStackTrace().toString()))
            }


        awaitClose { this.close() }
    }

    override fun readMessage(request: ReadMessageRequest): Flow<Resource<List<ChatRoomResponse>>> = callbackFlow {
        trySend(Resource.Loading())

        db.child(request.sender_id.toString()).child(request.receive_id.toString())
            .child(Endpoints.CHAT_ROOM).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<ChatRoomResponse>()

                    snapshot.children.forEach {
                        val data = it.getValue(ChatRoomResponse::class.java)
                        if (data != null) list.add(data)
                    }
                    trySend(Resource.Success(list))
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(Resource.Error(error.message))
                }
            })

        awaitClose { this.close() }
    }

    override fun createChatGroup(request: ChatUserRequest): Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading())

        // crete group for sender
        db.child(request.sender_id.toString()).child(request.receiver_id.toString()).setValue(request.toReceiverMap())
            .addOnSuccessListener {
                // create group for receiver
                db.child(request.receiver_id.toString()).child(request.sender_id.toString()).setValue(request.toSenderMap())
                    .addOnSuccessListener {
                        trySend(Resource.Success(true))
                    }
                    .addOnFailureListener { trySend(Resource.Error(it.localizedMessage)) }
            }
            .addOnFailureListener { trySend(Resource.Error(it.localizedMessage)) }



        awaitClose { this.close() }
    }

    override fun getUserMessage(id: Int): Flow<Resource<List<ChatUserResponse>>> = callbackFlow {
        trySend(Resource.Loading())

        db.child(id.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<ChatUserResponse>()

                snapshot.children.forEach {
                    val data = it.getValue(ChatUserResponse::class.java)
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
        val db = FirebaseDatabase.getInstance().getReference(Endpoints.CHAT)
    }

}