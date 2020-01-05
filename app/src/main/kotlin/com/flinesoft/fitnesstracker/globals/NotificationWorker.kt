package com.flinesoft.fitnesstracker.globals

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    enum class RequestParamKey { CHANNEL, TITLE, MESSAGE }
    enum class ResultParamKey { NOTIFICATION_ID }

    override fun doWork(): Result {
        val channel = NotificationHelper.Channel.valueOf(inputData.getString(RequestParamKey.CHANNEL.name)!!)
        val title = inputData.getString(RequestParamKey.TITLE.name)!!
        val message = inputData.getString(RequestParamKey.MESSAGE.name)!!

        val notificationIdentifier = NotificationHelper.sendNotification(applicationContext, channel, title, message)
        return Result.success(workDataOf(ResultParamKey.NOTIFICATION_ID.name to notificationIdentifier))
    }
}
