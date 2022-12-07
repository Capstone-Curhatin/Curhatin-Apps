package com.capstone.core.data.source

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.data.source.firebase.ChatDoctorStorage
import com.capstone.core.utils.Endpoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

class ChatDoctorDataSource : ChatDoctorStorage {

    override fun getUserMessage(id: String): Flow<Resource<List<ChatUserResponse>>> = callbackFlow {
        trySend(Resource.Loading())

        db.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<ChatUserResponse>()

                snapshot.children.forEach {
                    val data = it.getValue(ChatUserResponse::class.java)
                    if (data != null && data.last_date?.isNotEmpty() == true) list.add(data)
                }

                // send response
                trySend(Resource.Success(list.sortedByDescending { it.last_date }))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
            }
        })

        awaitClose { this.close() }
    }


    override fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading())

        request.id = db.push().key.toString()

        // set data for sender
        db.child(request.sender_id.toString()).child(request.receiver_id.toString()).child(Endpoints.CHAT_ROOM)
            .child(request.id.toString()).setValue(request.toMessageMap())
            .addOnSuccessListener {
                db.child(request.sender_id.toString()).child(request.receiver_id.toString()).updateChildren(request.toSender())

                // set data for receiver
                db.child(request.receiver_id.toString()).child(request.sender_id.toString()).child(Endpoints.CHAT_ROOM)
                    .child(request.id.toString()).setValue(request.toMessageMap())
                    .addOnSuccessListener {
                    db.child(request.receiver_id.toString()).child(request.sender_id.toString()).updateChildren(request.toReceiver())

                        // get unread messages from sender
                        val query = db.child(request.sender_id.toString()).child(request.receiver_id.toString()).child(Endpoints.CHAT_ROOM)
                            .orderByChild("read").equalTo(false)

                        query.get().addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                val count = task.result.childrenCount.toInt()
                                Timber.d("COUNT: $count")
                                request.unread = count
                                db.child(request.receiver_id.toString()).child(request.sender_id.toString()).updateChildren(request.toReceiver())
                            }
                        }

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

    companion object {
        val db = FirebaseDatabase.getInstance().getReference(Endpoints.CHAT_DOCTOR)
    }

}