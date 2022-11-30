package com.capstone.core.data.request

import android.icu.text.SymbolTable
import com.google.firebase.database.Exclude

data class WaitingRoomRequest(
    val user_id: Int,
    val online: Boolean? = true,
    val date: String
) {

    @Exclude
    fun toMap(): Map<String, Any?> =
        mapOf(
            "user_id" to user_id,
            "online" to online,
            "date" to date
        )

    @Exclude
    fun updateMap(): Map<String, Any?> =
        mapOf(
            "online" to online
        )

}
