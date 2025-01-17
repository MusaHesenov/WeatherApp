package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.CurrentResponseApi
import com.example.weatherapp.model.ForecastResponseApi
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.server.ApiClient
import com.example.weatherapp.server.ApiServices
import retrofit2.Call

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {


    constructor() : this(WeatherRepository(ApiClient().getClient().create(ApiServices::class.java)))

  fun loadCurrentWeather(lat: Double, lng: Double): Call<CurrentResponseApi> {
    return repository.getCurrentWeather(lat, lng)
}

    fun loadForecastWeather(lat: Double, lng: Double): Call<ForecastResponseApi> {
        return repository.getForecastWeather(lat, lng)
    }
    fun loadCountryWeather(q : String ) : Call<CurrentResponseApi> =
        repository.getCountryWeather(q)
}