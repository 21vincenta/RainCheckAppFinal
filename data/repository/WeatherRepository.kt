package data.repository

import api.WeatherApiService
import data.models.WeatherResponse
import data.models.ForecastResponse

class WeatherRepository(private val apiService: WeatherApiService) {
    suspend fun getCurrentWeather(city: String, apiKey: String, units: String = "metric"): WeatherResponse {
        return apiService.getCurrentWeather(city, apiKey, units)
    }

    suspend fun getForecast(city: String, apiKey: String, units: String = "metric"): ForecastResponse {
        return apiService.getForecast(city, apiKey, units)
    }
}
