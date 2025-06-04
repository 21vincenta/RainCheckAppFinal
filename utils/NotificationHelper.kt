package utils

import android.content.Context

class NotificationHelper(private val context: Context) {
    fun scheduleRainAlert() {
        // TODO: Implement scheduling logic using WorkManager or AlarmManager
    }
    fun scheduleDailySummary(timeHour: Int, timeMinute: Int) {
        // TODO: Implement scheduling logic for daily summary
    }
    fun cancelAll() {
        // TODO: Cancel all scheduled notifications
    }
}
