package com.example.weatherapp.repository

import com.example.weatherapp.server.ApiServices

class WeatherRepository(val api: ApiServices) {

    fun getCurrentWeather(lat: Double, lon: Double, units: String) =
        api.getCurrentWeather(lat, lon, "4faa4314b4ab01989d47c040c300d0d2", units)

    fun getForecastWeather(lat: Double, lon: Double, units: String) =
        api.getCurrentWeather(lat, lon, "4faa4314b4ab01989d47c040c300d0d2", units)

}