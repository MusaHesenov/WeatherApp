package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.model.CurrentResponseApi
import com.example.weatherapp.viewmodel.WeatherViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailFragment : Fragment() {
    private val weatherViewModel = WeatherViewModel()
    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var countryList: List<String> = listOf("London", "Paris", "New York", "Tokyo", "Sydney")
        var countryWeatherList: MutableList<CurrentResponseApi> = mutableListOf()

        for (country in countryList) {
            fetchCountryWeather(country)
            countryWeatherList.add(fetchCountryWeather(country).execute().body()!!)
        }


        val call = fetchCountryWeather("London")
        call.enqueue(object : Callback<CurrentResponseApi> {
            override fun onResponse(
                call: Call<CurrentResponseApi>,
                response: Response<CurrentResponseApi>
            ) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    println("Temperature: ${weatherData?.weather}")
                }
            }

            override fun onFailure(call: Call<CurrentResponseApi>, t: Throwable) {
                println("Failed to fetch weather: ${t.message}")
            }
        })


    }

    private fun fetchCountryWeather(query: String): Call<CurrentResponseApi> {
        var weather = weatherViewModel.loadCountryWeather(query)

        return weather

    }
}