package com.capstone.curhatin.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.capstone.core.data.common.ErrorParser
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.SafeCall
import com.capstone.core.data.network.AuthService
import com.capstone.core.data.request.auth.FcmRequest
import com.capstone.core.utils.MySharedPreference
import com.capstone.curhatin.BuildConfig
import com.capstone.curhatin.MainActivity
import com.capstone.curhatin.R
import com.capstone.curhatin.viewmodel.AuthViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager

    @Inject lateinit var prefs: MySharedPreference

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.e("NEW TOKEN: $token")
        prefs.setFcm(token)
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Timber.d("onMessageReceive From: ${message.from}")

        if (message.data.isNotEmpty()){
            val notification = showNotification(message)
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun showNotification(message: RemoteMessage): Notification {
        // Get data
        val titleText = message.data["title"]
        val mainNotificationText = message.data["body"]
        val titleTextNotification = BuildConfig.BUILD_TYPE

        // Create Notification Channel for O+ and beyond devices (26+).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANEL, titleTextNotification, NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(notificationChannel)
        }

        // Build the BIG_TEXT_STYLE.
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(mainNotificationText)
            .setBigContentTitle(titleText)

        // Set up main Intent/Pending Intents for notification.
        val launchActivityIntent = Intent(this, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            this, 0,launchActivityIntent, PendingIntent.FLAG_IMMUTABLE or 0
        )

        val cancelIntent = Intent(this, FirebaseMessagingService::class.java)
        cancelIntent.putExtra("${applicationContext.packageName}.extra.CANCEL_NOTIFICATION", true)

        // Build and issue the notification.
        // Notification Channel Id is ignored for Android pre O (26).

        val notificationCompatBuilder =
            NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANEL)

        return notificationCompatBuilder
            .setStyle(bigTextStyle)
            .setVibrate(LongArray(1))
            .setContentTitle(mainNotificationText)
            .setSmallIcon(R.drawable.logo_round)
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(activityPendingIntent)
            .build()
    }

    companion object {
        private const val NOTIFICATION_ID = 103242
        private const val NOTIFICATION_CHANEL = "com.curhatin.notification_channel"
    }
}