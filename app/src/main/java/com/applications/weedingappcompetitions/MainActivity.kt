package com.applications.weedingappcompetitions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.applications.weedingappcompetitions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        val localStorage = LocalStorage(context = this)
        localStorage.clearSharedPreference()
        _binding = null
    }
}