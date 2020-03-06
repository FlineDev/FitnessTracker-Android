package com.flinesoft.fitnesstracker.ui.workouts

import android.app.Application
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.flinesoft.fitnesstracker.R
import com.flinesoft.fitnesstracker.globals.AppPreferences
import com.flinesoft.fitnesstracker.globals.DEFAULT_REMINDER_DAYS_COUNT
import com.flinesoft.fitnesstracker.globals.NotificationHelper
import com.flinesoft.fitnesstracker.globals.PREVENT_NEXT_DAY_WHEN_WORKOUT_WITHIN_HOURS
import com.flinesoft.fitnesstracker.globals.extensions.database
import com.flinesoft.fitnesstracker.globals.extensions.plusKt
import com.flinesoft.fitnesstracker.model.Impediment
import com.flinesoft.fitnesstracker.model.Recoverable
import com.flinesoft.fitnesstracker.model.Workout
import org.joda.time.DateTime
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@ExperimentalTime
class WorkoutsViewModel(application: Application) : AndroidViewModel(application) {
    private val latestWorkouts = database().workoutDao.allOrderedByEndDateDescending()
    private val latestImpediments = database().impedimentDao.allOrderedByEndDateDescending()

    val latestRecoverables: LiveData<List<Recoverable>> = MediatorLiveData<List<Recoverable>>().apply {
        addSource(latestWorkouts) { value = mergedRecoverables(workouts = it, impediments = latestImpediments.value ?: emptyList()) }
        addSource(latestImpediments) { value = mergedRecoverables(workouts = latestWorkouts.value ?: emptyList(), impediments = it) }
    }

    fun suggestedNextWorkoutDateString(): String = DateUtils.getRelativeTimeSpanString(
        suggestedNextWorkoutDate().millis,
        DateTime.now().millis,
        DateUtils.DAY_IN_MILLIS
    ).toString()

    private fun mergedRecoverables(workouts: List<Workout>, impediments: List<Impediment>): List<Recoverable> =
            (workouts + impediments).sortedByDescending { it.endDate }

    private fun suggestedNextWorkoutDate(): DateTime {
        return latestRecoverables.value?.firstOrNull()
            ?.recoveryEndDate?.plusHours(24 - PREVENT_NEXT_DAY_WHEN_WORKOUT_WITHIN_HOURS)
            ?: DateTime.now()
    }

    fun updateReminders() {
        if (AppPreferences.onDayReminderOn) {
            var nextReminderDate = suggestedNextWorkoutDate()
                .withTimeAtStartOfDay()
                .plusKt(AppPreferences.onDayReminderDelay)

            while (nextReminderDate < DateTime.now()) {
                nextReminderDate = nextReminderDate.plusDays(1)
            }

            if (AppPreferences.lastScheduledReminderDate != nextReminderDate) {
                NotificationHelper.cancelScheduledNotificationsInChannel(getApplication(), NotificationHelper.Channel.WORKOUT_REMINDERS)

                for (reminderDayIndex in 0 until DEFAULT_REMINDER_DAYS_COUNT) {
                    NotificationHelper.scheduleNotification(
                        context = getApplication(),
                        channel = NotificationHelper.Channel.WORKOUT_REMINDERS,
                        title = getApplication<Application>().getString(R.string.notifications_workout_reminders_today_title),
                        message = getApplication<Application>().getString(R.string.notifications_workout_reminders_today_message),
                        date = nextReminderDate.plusDays(reminderDayIndex),
                        autoCancel = true,
                        timeoutAfter = 18.hours
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
