package viewmodel

import androidx.lifecycle.*
import data.repository.WeatherRepository
import data.models.WeatherResponse
import data.models.ForecastResponse
import kotlinx.coroutines.launch

class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {
    private val _currentWeather = MutableLiveData<WeatherResponse>()
    val currentWeather: LiveData<WeatherResponse> = _currentWeather

    private val _forecast = MutableLiveData<ForecastResponse>()
    val forecast: LiveData<ForecastResponse> = _forecast

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // Umbrella and shoe advice
    private val _umbrellaAdvice = MutableLiveData<String>()
    val umbrellaAdvice: LiveData<String> = _umbrellaAdvice

    private val _shoeAdvice = MutableLiveData<String>()
    val shoeAdvice: LiveData<String> = _shoeAdvice

    fun fetchWeather(city: String, apiKey: String, units: String = "metric") {
        _loading.value = true
        viewModelScope.launch {
            try {
                val weather = repo.getCurrentWeather(city, apiKey, units)
                _currentWeather.value = weather
                val forecastData = repo.getForecast(city, apiKey, units)
                _forecast.value = forecastData
                _umbrellaAdvice.value = getUmbrellaAdvice(weather, forecastData)
                _shoeAdvice.value = getShoeAdvice(forecastData)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    private fun getUmbrellaAdvice(weather: WeatherResponse, forecast: ForecastResponse): String {
        // If current weather is rain (id 500-531) or rain in next 6 hours
        val isRaining = weather.weather.any { it.id in 500..531 }
        val rainInNext6h = forecast.forecastList.take(2).any { it.weather.any { w -> w.id in 500..531 } }
        return if (isRaining || rainInNext6h) "Yes, bring an umbrella" else "No, you're fine"
    }

    private fun getShoeAdvice(forecast: ForecastResponse): String {
        // If any of next 12 hours has rain, snow, or wet conditions
        val wetCondition = forecast.forecastList.take(4).any { it.weather.any { w -> w.id in 500..622 } }
        return if (wetCondition) "Wear waterproof shoes" else "Regular shoes are fine"
    }
}
