package com.flinesoft.fitnesstracker.globals

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.flinesoft.fitnesstracker.R
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
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

    fun scheduleNotification(context: Context, channel: Channel, title: String, message: String, date: DateTime, autoCancel: Boolean, timeoutAfter: Duration) {
        val notificationWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>().apply {
            setInputData(
                workDataOf(
                    NotificationWorker.RequestParamKey.CHANNEL.name to channel.name,
                    NotificationWorker.RequestParamKey.TITLE.name to title,
                    NotificationWorker.RequestParamKey.MESSAGE.name to message,
                    NotificationWorker.RequestParamKey.AUTO_CANCEL.name to autoCancel,
                    NotificationWorker.RequestParamKey.TIMEOUT_AFTER_MILLIS.name to timeoutAfter.toLongMilliseconds()
                )
            )
            setInitialDelay(date.millis - DateTime.now().millis, TimeUnit.MILLISECONDS)
            addTag(channel.name)
        }.build()
        WorkManager.getInstance(context).enqueue(notificationWorkRequest)
    }

    fun cancelScheduledNotificationsInChannel(context: Context, channel: Channel) {
        WorkManager.getInstance(context).cancelAllWorkByTag(channel.name)
    }

    /**
     * Sends a notification immediately and returns it's identifier.
     */
    fun sendNotification(context: Context, channel: Channel, title: String, message: String, autoCancel: Boolean, timeoutAfter: Duration): Int {
        val notification = buildNotification(context, channel, title, message, autoCancel, timeoutAfter)
        val identifier = SystemClock.uptimeMillis().toInt()
        NotificationManagerCompat.from(context).notify(context.packageName, identifier, notification)
        return identifier
    }

    private fun buildNotification(
        context: Context,
        channel: Channel,
        title: String,
        message: String,
        autoCancel: Boolean,
        timeoutAfter: Duration
    ): Notification = NotificationCompat.Builder(context, channel.name).apply {
        setSmallIcon(R.drawable.ic_notification_small)
        setContentTitle(title)
        setContentText(message)

        setAutoCancel(autoCancel)
        if (timeoutAfter.isPositive()) {
            setTimeoutAfter(timeoutAfter.toLongMilliseconds())
        }

        val appIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        setContentIntent(PendingIntent.getActivity(context, 0, appIntent, 0))
        priority = NotificationCompat.PRIORITY_DEFAULT

        if (message.length > 25) {
            setStyle(NotificationCompat.BigTextStyle().bigText(message))
        }
    }.build()
}
