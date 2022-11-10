package com.capstone.core.data.response

import com.google.gson.annotations.SerializedName

data class GenericResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String
)