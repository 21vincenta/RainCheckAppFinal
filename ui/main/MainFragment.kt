package ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import viewmodel.WeatherViewModel
import data.repository.WeatherRepository
import api.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.raincheck.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    // Normally use DI, here manual for simplicity
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(
            Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiKey = BuildConfig.WEATHER_API_KEY
        val defaultCity = "New York" // Replace with PreferenceHelper
        binding.editTextCity.setText(defaultCity)
        binding.editTextCity.setOnEditorActionListener { v, actionId, event ->
            val city = binding.editTextCity.text.toString()
            viewModel.fetchWeather(city, apiKey)
            true
        }
        viewModel.currentWeather.observe(viewLifecycleOwner, Observer { weather ->
            binding.textViewCurrentTemp.text = "${weather.main.temp}°C"
            binding.textViewCurrentCondition.text = weather.weather.firstOrNull()?.description ?: "-"
            binding.textViewFeelsLike.text = "Feels like ${weather.main.feels_like}°C"
            binding.textViewHumidity.text = "Humidity ${weather.main.humidity}%"
        })
        viewModel.umbrellaAdvice.observe(viewLifecycleOwner, Observer {
            binding.textViewUmbrellaAdvice.text = it
        })
        viewModel.shoeAdvice.observe(viewLifecycleOwner, Observer {
            binding.textViewShoeAdvice.text = it
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        })
        // Initial fetch
        viewModel.fetchWeather(defaultCity, apiKey)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
