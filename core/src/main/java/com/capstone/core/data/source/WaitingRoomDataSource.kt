package com.capstone.core.data.source

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.source.firebase.WaitingRoomStorage
import com.capstone.core.utils.Constant
import com.capstone.core.utils.Endpoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class WaitingRoomDataSource : WaitingRoomStorage {

    override fun createRoom(request: WaitingRoomRequest): Flow<Resource<GenericResponse>> = callbackFlow {
        trySend(Resource.Loading())

        waiting_room.child(request.user_id.toString()).updateChildren(request.toMap())
            .addOnSuccessListener {
                val response = GenericResponse(true, Constant.WAITING_ADDED_STATUS)
                trySend(Resource.Success(response))
            }
            .addOnFailureListener {
                trySend(Resource.Error(Constant.WAITING_FAILURE_STATUS))
            }
        awaitClose { this.cancel() }
    }

    override fun updateStatus(request: WaitingRoomRequest): Flow<Resource<GenericResponse>> = callbackFlow {
        trySend(Resource.Loading())

        waiting_room.child(request.user_id.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    waiting_room.child(request.user_id.toString()).updateChildren(request.updateMap())
                    val response = GenericResponse(true, "OK")
                    trySend(Resource.Success(response))
                }else{
                    trySend(Resource.Error(Constant.UNKNOWN_ERROR))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
            }
        })

        awaitClose { this.cancel() }
    }

    companion object {
        val waiting_room = FirebaseDatabase.getInstance().getReference(Endpoints.WAITING_ROOM)
    }

}