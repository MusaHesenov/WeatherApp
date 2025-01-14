package com.example.weatherapp.server

import com.example.weatherapp.model.CurrentResponseApi
import com.example.weatherapp.model.ForecastResponseApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") ApiKey:String,

    ): Call<CurrentResponseApi>


    @GET("data/2.5/forecast")
    fun getForecastWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") ApiKey:String,

    ): Call<ForecastResponseApi>


    @GET("data/2.5/weather")
    fun getCountryWeather(
        @Query("q") country:String,
        @Query("appid") ApiKey:String,

        ): Call<CurrentResponseApi>

}