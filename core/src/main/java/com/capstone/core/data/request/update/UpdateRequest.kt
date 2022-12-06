package com.capstone.core.data.request.update

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

data class UpdateRequest(
    val name: String? = null,
    val phone: String? = null,
    val picture: File? = null
) {
    fun updateUser(): RequestBody =
        MultipartBody.Builder().setType(MultipartBody.FORM).apply {
            addFormDataPart("name", name.toString())
            addFormDataPart("phone", phone.toString())
            picture?.let { addFormDataPart("picture", "${picture.name}.jpg", it.asRequestBody("multipart/form-data".toMediaTypeOrNull())) }
        }.build()

    fun updateUserAnonym(): RequestBody =
        MultipartBody.Builder().setType(MultipartBody.FORM).apply {
            addFormDataPart("name", name.toString())
            addFormDataPart("phone", phone.toString())
        }.build()
}
