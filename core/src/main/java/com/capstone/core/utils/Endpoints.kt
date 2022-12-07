package com.capstone.core.utils

object Endpoints {

    // Auth
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val USER_VERIFICATION = "user_verification"
    const val REQUEST_OTP = "request_otp"
    const val VERIFY_OTP = "verify_otp"
    const val UPDATE_FCM = "update_fcm"
    const val LOGOUT = "logout"
    const val UPDATE_PASSWORD = "updatePassword"
    const val SEND_NOTIFICATION = "sendNotification"

    // Story
    const val CREATE_STORY = "createStory"
    const val GET_STORY = "getAllStory"
    const val GET_CATEGORY = "getAllCategory"
    const val GET_STORY_BY_USER = "getStoryByUser"
    const val GET_STORY_BY_CATEGORY = "getStoryByCategory/{category_id}/"
    const val INCREMENT_COMMENTS = "incrementComment/{id}"
    const val DECREMENT_COMMENTS = "decrementComment/{id}"

    // Firebase
    const val FETCH_USER = "fetch"
    const val UPDATE = "update"

    // Chat
    const val WAITING_ROOM = "waiting_room"
    const val CHAT = "chat"
    const val CHAT_ROOM = "_chat_room"
    const val NOTIFICATION = "notification"
    const val MY_NOTIFICATION = "my_notification"
    const val COMMENT = "comment"

    // Chat Doctor
    const val CHAT_DOCTOR = "chat_doctor"
    const val CHAT_ROOM_DOCTOR = "_chat_room_doctor"

    //Doctor
    const val GET_DOCTOR = "getDoctor"
    const val DETAIL_DOCTOR = "detailDoctor/{id}"

}