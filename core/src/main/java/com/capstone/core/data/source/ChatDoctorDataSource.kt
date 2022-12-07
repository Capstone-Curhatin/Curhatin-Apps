package com.capstone.core.data.source

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.data.source.firebase.ChatDoctorStorage
import com.capstone.core.utils.Endpoints
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ChatDoctorDataSource : ChatDoctorStorage {

    override fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>> = callbackFlow {
        trySend(Resource.Loading())

        request.id = db.push().key.toString()

        // set data for sender
        db.child(request.sender_id).child(request.receiver_id).child(Endpoints.CHAT_ROOM_DOCTOR)
            .child(request.id.toString()).setValue(request.toMessageMap())
            .addOnSuccessListener {
                db.child(request.sender_id).child(request.receiver_id).updateChildren(request.toSender())

                // set data for receiver
                db.child(request.receiver_id).child(request.sender_id).child(Endpoints.CHAT_ROOM_DOCTOR)
                    .child(request.id.toString()).setValue(request.toMessageMap())
                    .addOnSuccessListener {

                        // get unread messages from sender
                        val query = ChatDataSource.db.child(request.sender_id).child(request.receiver_id).child(Endpoints.CHAT_ROOM_DOCTOR)
                            .orderByChild("read").equalTo(false)

                        query.get().addOnCompleteListener { task ->
                            val count = task.result.childrenCount.toInt()
                            request.unread = count
                            db.child(request.receiver_id).child(request.sender_id).updateChildren(request.toReceiver())
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