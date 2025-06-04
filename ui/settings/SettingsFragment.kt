package ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.raincheck.databinding.FragmentSettingsBinding
import utils.PreferenceHelper

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Load preferences
        val prefs = PreferenceHelper(requireContext())
        binding.radioCelsius.isChecked = prefs.isCelsius()
        binding.radioFahrenheit.isChecked = !prefs.isCelsius()
        binding.switchAutoLocation.isChecked = prefs.isAutoLocation()
        binding.editTextDefaultCity.setText(prefs.getDefaultCity())
        binding.switchRainAlerts.isChecked = prefs.isRainAlertsEnabled()
        binding.switchDailySummary.isChecked = prefs.isDailySummaryEnabled()
        // Save on change
        binding.radioCelsius.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) prefs.setCelsius(true)
        }
        binding.radioFahrenheit.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) prefs.setCelsius(false)
        }
        binding.switchAutoLocation.setOnCheckedChangeListener { _, isChecked ->
            prefs.setAutoLocation(isChecked)
        }
        binding.editTextDefaultCity.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) prefs.setDefaultCity(binding.editTextDefaultCity.text.toString())
        }
        binding.switchRainAlerts.setOnCheckedChangeListener { _, isChecked ->
            prefs.setRainAlertsEnabled(isChecked)
        }
        binding.switchDailySummary.setOnCheckedChangeListener { _, isChecked ->
            prefs.setDailySummaryEnabled(isChecked)
        }
        // Notification time picker can be added similarly
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
