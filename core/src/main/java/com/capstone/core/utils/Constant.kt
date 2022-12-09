package com.capstone.core.utils

import com.google.firebase.database.FirebaseDatabase


/**
     write in this file if you want create a static value
 */
object Constant {

    const val UNKNOWN_ERROR = "Unknown error occurred, please try again later."
    const val TIMEOUT_ERROR = "The server took too long to respond, please try again later."

    const val SUCCESS_CREATE_STORY = "Congratulations, your story has been successfully uploaded"

    const val WAITING_ADDED_STATUS = "Success added to waiting room. We will contact you again"
    const val WAITING_FAILURE_STATUS = "Failed add to waiting room. Try again later!"
    const val FINDING_EMPTY = "waiting empty"
    const val USER_FINDING_MESSAGE = "We are finding your partner"
    const val USER_LISTEN_NOT_FOUND = "Sorry, we can't find partner for you. Try again later :)"

    const val ANONYMOUS = "Anonymous"
    const val ANONYMOUS_IMAGE = "https://st4.depositphotos.com/11634452/21365/v/600/depositphotos_213659488-stock-illustration-picture-profile-icon-human-or.jpg"

    const val NOTIFICATION_CHAT = "send message"
    const val NOTIFICATION_STORY = "reply your story"
    const val TYPE_CHAT = "type_chat"
    const val TYPE_COMMENT = "type_comment"

    const val SPLASH_DURATION: Long = 1500
    const val FINDING_DURATION: Long = 180000
    const val STARTING_INDEX = 1
    val CHAT_FIREBASE_INSTANCE = FirebaseDatabase.getInstance().getReference(Endpoints.CHAT)
}