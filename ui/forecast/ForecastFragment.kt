package ui.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ui.adapters.ForecastAdapter
import viewmodel.WeatherViewModel
import data.repository.WeatherRepository
import api.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.raincheck.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {
    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(
            Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        ))
    }

    private lateinit var forecastAdapter: ForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastAdapter = ForecastAdapter()
        binding.recyclerViewForecast.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewForecast.adapter = forecastAdapter
        val apiKey = BuildConfig.WEATHER_API_KEY
        val defaultCity = "New York" // Replace with PreferenceHelper
        viewModel.forecast.observe(viewLifecycleOwner, Observer { forecast ->
            forecastAdapter.submitList(forecast.forecastList)
        })
        // Initial fetch
        viewModel.fetchWeather(defaultCity, apiKey)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
