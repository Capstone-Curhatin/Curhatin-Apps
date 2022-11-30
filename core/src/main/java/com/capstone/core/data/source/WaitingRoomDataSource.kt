package com.capstone.core.data.source

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.chat.WaitingRoomResponse
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
import timber.log.Timber

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

    override fun getPriority(id: Int): Flow<Resource<Int>> = callbackFlow {
        trySend(Resource.Loading())

        waiting_room.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val list = ArrayList<WaitingRoomResponse>()

                snapshot.children.forEach {
                    val data = it.getValue(WaitingRoomResponse::class.java)
                    if (data != null && data.user_id != id) list.add(data)
                }

                val priorityDate = list.sortedBy { it.date }.take(list.size).toTypedArray()
                val priority = priorityDate.filter { it.online == true }

                Timber.d("PRIORITY: $priority")

                if (priority.isEmpty()){
                    trySend(Resource.Success(0))
                }else{
                    trySend(Resource.Success(priority[0].user_id!!))
                    waiting_room.child(priority[0].user_id.toString()).removeValue()
                    waiting_room.removeEventListener(this)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
            }
        })

        awaitClose { this.close() }
    }


    companion object {
        val waiting_room = FirebaseDatabase.getInstance().getReference(Endpoints.WAITING_ROOM)
    }

}