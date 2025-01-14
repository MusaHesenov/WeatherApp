package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.CurrentResponseApi
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.server.ApiClient
import com.example.weatherapp.server.ApiServices

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {

    private val _countryWeather = MutableLiveData<CurrentResponseApi>()
    val countryWeather: LiveData<CurrentResponseApi> get() = _countryWeather

    constructor() : this(WeatherRepository(ApiClient().getClient().create(ApiServices::class.java)))

    fun loadCurrentWeather(lat: Double, lng: Double) =
        repository.getCurrentWeather(lat, lng, )


    fun loadForecastWeather(lat: Double, lng: Double) =
        repository.getForecastWeather(lat, lng, )

    fun loadCountryWeather(q : String ) =
        repository.getCountryWeather(q)
}