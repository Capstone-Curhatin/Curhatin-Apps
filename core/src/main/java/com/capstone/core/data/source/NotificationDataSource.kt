package com.capstone.core.data.source

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.notification.CreateNotificationRequest
import com.capstone.core.data.response.NotificationResponse
import com.capstone.core.data.source.firebase.NotificationStorage
import com.capstone.core.utils.Endpoints
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

class NotificationDataSource : NotificationStorage {

    override fun createNotification(request: CreateNotificationRequest): Flow<Resource<Boolean>> = callbackFlow {

        trySend(Resource.Loading())

        request.id =  db.push().key.toString()
        db.child(request.receiver_id.toString()).child(request.id.toString()).setValue(request.toMap())
            .addOnSuccessListener { trySend(Resource.Success(true)) }
            .addOnFailureListener { trySend(Resource.Error(it.localizedMessage)) }

        awaitClose { this.close() }
    }

    override fun getNotification(id: String): Flow<Resource<List<NotificationResponse>>> = callbackFlow {
        trySend(Resource.Loading())

        db.child(id).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val notification = ArrayList<NotificationResponse>()
                for (items in snapshot.children){
                    val data = items.getValue(NotificationResponse::class.java)
                    if (data != null) notification.add(data)
                }

                // Send response
                trySend(Resource.Success(notification))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error(error.message))
            }
        })

        awaitClose { this.close() }
    }

    override fun countNotification(id: String): Flow<Resource<Int>> = callbackFlow {
        trySend(Resource.Loading())

        // count unread notification
        val query = db.child(id).orderByChild("read").equalTo(false)
        query.get().addOnCompleteListener { task ->
            if (task.isSuccessful){
                val sum = task.result.childrenCount.toInt()
                trySend(Resource.Success(sum))
            }
        }

        awaitClose {this.close()}
    }

    companion object {
        val db = FirebaseDatabase.getInstance().getReference(Endpoints.NOTIFICATION)
    }

}