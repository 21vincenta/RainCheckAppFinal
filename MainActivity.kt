package com.example.raincheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ui.main.MainFragment
import ui.forecast.ForecastFragment
import ui.settings.SettingsFragment
import com.example.raincheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Default fragment
        showFragment(MainFragment())
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_main -> showFragment(MainFragment())
                R.id.menu_forecast -> showFragment(ForecastFragment())
                R.id.menu_settings -> showFragment(SettingsFragment())
            }
            true
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
