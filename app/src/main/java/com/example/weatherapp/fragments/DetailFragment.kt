package com.example.weatherapp.fragments

import VerticalSpaceItemDecoration
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapters.ForecastAdapter
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.databinding.WeatherRvBinding
import com.example.weatherapp.model.CurrentResponseApi
import com.example.weatherapp.model.ForecastResponseApi
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class DetailFragment : Fragment() {
    private val weatherViewModel = WeatherViewModel()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var binding1: WeatherRvBinding
    private val weatherAdapter by lazy { WeatherAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // View Binding kullanımı
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryList = listOf("London", "Paris", "New York", "Tokyo", "Sydney")
        val countryWeatherList = mutableListOf<CurrentResponseApi>()


        binding.detailBackBtn.setOnClickListener{
            findNavController().navigateUp()
        }


        // Coroutine başlat
        viewLifecycleOwner.lifecycleScope.launch {
            for (country in countryList) {
                try {
                    // Ağ isteğini arka planda çalıştır
                    val weatherData = fetchCountryWeather(country)
                    weatherData?.let {
                        countryWeatherList.add(it)
                        println("Temperature in $country: ${it.weather}")
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Failed to fetch weather for $country", Toast.LENGTH_SHORT).show()
                }
            }

            weatherAdapter.differ.submitList(countryWeatherList)
            binding.weatherRv.apply {
                adapter = weatherAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                addItemDecoration(VerticalSpaceItemDecoration(35))
            }

            updateUI(countryWeatherList)
        }
    }


    private suspend fun fetchCountryWeather(query: String): CurrentResponseApi? {
        return withContext(Dispatchers.IO) {
            try {
                val response = weatherViewModel.loadCountryWeather(query).execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    /*
        // Weather API'ından verileri çek
                        /* val drawable = if(isNightNow()) R.drawable.night_bg
                         else{
                             setDynamicallyWallpaper(it.weather?.get(0)?.icon?:"-")
                         }
                         bgImage.setImageResource(drawable)
                         setEffectRainSnow(it.weather?.get(0)?.icon?:"-")

                         */
                    }
                }
            }
        */

    // UI update function
    private fun updateUI(countryWeatherList: List<CurrentResponseApi>) {
        println("Weather list size: ${countryWeatherList.size}")
    }
}

