package utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("raincheck_prefs", Context.MODE_PRIVATE)

    fun isCelsius(): Boolean = prefs.getBoolean("unit_celsius", true)
    fun setCelsius(value: Boolean) = prefs.edit().putBoolean("unit_celsius", value).apply()

    fun isAutoLocation(): Boolean = prefs.getBoolean("auto_location", true)
    fun setAutoLocation(value: Boolean) = prefs.edit().putBoolean("auto_location", value).apply()

    fun getDefaultCity(): String = prefs.getString("default_city", "New York") ?: "New York"
    fun setDefaultCity(city: String) = prefs.edit().putString("default_city", city).apply()

    fun isRainAlertsEnabled(): Boolean = prefs.getBoolean("rain_alerts", false)
    fun setRainAlertsEnabled(value: Boolean) = prefs.edit().putBoolean("rain_alerts", value).apply()

    fun isDailySummaryEnabled(): Boolean = prefs.getBoolean("daily_summary", false)
    fun setDailySummaryEnabled(value: Boolean) = prefs.edit().putBoolean("daily_summary", value).apply()
}
