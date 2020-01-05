package com.flinesoft.fitnesstracker.globals

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.flinesoft.fitnesstracker.R

object NotificationHelper {
    enum class Channel { WORKOUT_REMINDERS }

    fun setup(context: Context) {
        val notificationChannels: List<NotificationChannel> = listOf(
            NotificationChannel(
                Channel.WORKOUT_REMINDERS.name,
                context.getString(R.string.notifications_workout_reminders_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        for (channel in notificationChannels) {
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Sends a notification immediately and returns it's identifier.
     */
    fun sendNotification(context: Context, channel: Channel, title: String, message: String): Int {
        val notification = buildNotification(context, channel, title, message)
        val identifier = SystemClock.uptimeMillis().toInt()
        NotificationManagerCompat.from(context).notify(context.packageName, identifier, notification)
        return identifier
    }

    private fun buildNotification(
        context: Context,
        channel: Channel,
        title: String,
        message: String
    ): Notification = NotificationCompat.Builder(context, channel.name).apply {
        setSmallIcon(R.drawable.ic_notification_small)
        setContentTitle(title)
        setContentText(message)

        if (message.length > 25) {
            setStyle(NotificationCompat.BigTextStyle().bigText(message))
        }
    }.build()
}
