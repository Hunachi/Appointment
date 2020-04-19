package io.github.hunachi.appointment

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.FirebaseMessagingService

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            val data = remoteMessage.data.toString().contains("\"accepted\": true")
            startNotification(data)
        }
    }

    private fun startNotification(isAccept: Boolean) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = "appointment_result"
        val name = "アポの返事がきました！"
        val notifyDescription = "アポイントメントが承認されたか拒否されたかを通知します．"
        // Channelの取得と生成
        if (notificationManager.getNotificationChannel(id) == null) {
            val mChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            mChannel.apply {
                description = notifyDescription
            }
            notificationManager.createNotificationChannel(mChannel)
        }
        val resultString = if (isAccept) "アポが承認されました！" else "アポが拒否されました。"
        val notification = NotificationCompat
            .Builder(this, id)
            .apply {
                setSmallIcon(R.drawable.ic_date_range_black_24dp)
                setContentTitle(resultString)
                setContentText("")
            }.build()
        notificationManager.notify(SystemClock.uptimeMillis().toInt(), notification)
    }
}

