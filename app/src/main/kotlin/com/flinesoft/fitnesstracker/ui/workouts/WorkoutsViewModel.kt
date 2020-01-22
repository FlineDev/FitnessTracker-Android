package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.flinesoft.fitnesstracker.globals.DEFAULT_REMINDER_DAYS_COUNT
import com.flinesoft.fitnesstracker.globals.NotificationHelper
import com.flinesoft.fitnesstracker.globals.PREVENT_NEXT_DAY_WHEN_WORKOUT_WITHIN_HOURS
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.model.Workout
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutsViewModel(application: Application) : AndroidViewModel(application) {
    val workouts: LiveData<List<Workout>> = database().workoutDao.allOrderedByEndDateDescending()

    fun suggestedNextWorkoutDateString(): String = DateUtils.getRelativeTimeSpanString(
        suggestedNextWorkoutDate().millis,
        DateTime.now().millis,
        DateUtils.DAY_IN_MILLIS
    ).toString()

    private fun suggestedNextWorkoutDate(): DateTime = workouts.value?.firstOrNull()?.let { latestWorkout ->
        latestWorkout.endDate
            .plus(latestWorkout.recoveryDuration.toLongMilliseconds())
            .plusHours(24 - PREVENT_NEXT_DAY_WHEN_WORKOUT_WITHIN_HOURS)
    } ?: DateTime.now()

    fun updateReminders() {
        if (AppPreferences.onDayReminderOn) {
            var nextReminderDate = suggestedNextWorkoutDate()
                .withTimeAtStartOfDay()
                .plus(AppPreferences.onDayReminderDelay.toLongMilliseconds())

            while (nextReminderDate < DateTime.now()) {
                nextReminderDate = nextReminderDate.plusDays(1)
            }

            if (AppPreferences.lastScheduledReminderDate != nextReminderDate) {
                NotificationHelper.cancelScheduledNotificationsInChannel(getApplication(), NotificationHelper.Channel.WORKOUT_REMINDERS)

                for (reminderDayIndex in 0 until DEFAULT_REMINDER_DAYS_COUNT) {
                    NotificationHelper.scheduleNotification(
                        getApplication(),
                        NotificationHelper.Channel.WORKOUT_REMINDERS,
                        getApplication<Application>().getString(R.string.notifications_workout_reminders_today_title),
                        getApplication<Application>().getString(R.string.notifications_workout_reminders_today_message),
                        nextReminderDate.plusDays(reminderDayIndex)
                    )
                }

                AppPreferences.lastScheduledReminderDate = nextReminderDate
            }
        } else {
            NotificationHelper.cancelScheduledNotificationsInChannel(getApplication(), NotificationHelper.Channel.WORKOUT_REMINDERS)
            AppPreferences.lastScheduledReminderDate = null
        }
    }
}
