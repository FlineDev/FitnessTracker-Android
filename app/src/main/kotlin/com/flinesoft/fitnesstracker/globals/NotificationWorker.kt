package com.flinesoft.fitnesstracker.globals

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

@ExperimentalTime
class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    enum class RequestParamKey { CHANNEL, TITLE, MESSAGE, AUTO_CANCEL, TIMEOUT_AFTER_MILLIS }
    enum class ResultParamKey { NOTIFICATION_ID }

    override fun doWork(): Result {
        val channel = NotificationHelper.Channel.valueOf(inputData.getString(RequestParamKey.CHANNEL.name)!!)
        val title = inputData.getString(RequestParamKey.TITLE.name)!!
        val message = inputData.getString(RequestParamKey.MESSAGE.name)!!
        val autoCancel = inputData.getBoolean(RequestParamKey.AUTO_CANCEL.name, true)
        val timeoutAfter = inputData.getLong(RequestParamKey.TIMEOUT_AFTER_MILLIS.name, 0).milliseconds

        val notificationIdentifier = NotificationHelper.sendNotification(applicationContext, channel, title, message, autoCancel, timeoutAfter)
        return Result.success(workDataOf(ResultParamKey.NOTIFICATION_ID.name to notificationIdentifier))
    }
}
