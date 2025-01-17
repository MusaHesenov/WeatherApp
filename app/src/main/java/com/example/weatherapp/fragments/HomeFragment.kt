package com.example.weatherapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.viewmodel.WeatherViewModel
import android.graphics.Color
import android.icu.util.Calendar
import android.renderscript.RenderScript
import android.util.Log
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.ForecastAdapter
import com.example.weatherapp.model.CurrentResponseApi
import com.example.weatherapp.model.ForecastResponseApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val weatherViewModel = WeatherViewModel()
    private val calendar by lazy { Calendar.getInstance() }
    private val forecastAdapter by lazy { ForecastAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requireActivity().window.apply {
//            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            statusBarColor = Color.TRANSPARENT
//        }

        binding.detailWeathers.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }


        binding.apply {
            var lat = 40.409264
            var lon = 49.867092
            var name = "Baku"

            location.text = name
            progressBar.visibility = View.VISIBLE

            //Current Weather
            weatherViewModel.loadCurrentWeather(lat, lon).enqueue(object :
                retrofit2.Callback<CurrentResponseApi> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<CurrentResponseApi>,
                    response: Response<CurrentResponseApi>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        progressBar.visibility = View.GONE
                        detailLayout.visibility = View.VISIBLE
                        data?.let {
                            statusTxt.text = it.weather?.get(0)?.main ?: "-"
                            temprature.text =it.main?.temp.let {
                                it?.let { it1 ->
                                    Math.round(it1).toString()
                                }
                            } + "º"
                            high.text = "H: "+it.main?.tempMax.let {
                                it?.let { it1 ->
                                    Math.round(it1).toString()
                                }
                            } + "º"
                            low.text ="H: "+ it.main?.tempMin.let {
                                it?.let { it1 ->
                                    Math.round(it1).toString()
                                }
                            } + "º"

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


                override fun onFailure(call: Call<CurrentResponseApi>, t: Throwable) {
                    Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
                }
            })

            //Forecast Weather
            weatherViewModel.loadForecastWeather(lat,lon)
                .enqueue(object : retrofit2.Callback<ForecastResponseApi> {
                    override fun onResponse(
                        call: Call<ForecastResponseApi>,
                        response: Response<ForecastResponseApi>
                    ) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            //blueView.visibility = View.VISIBLE

                            data?.let {
                                forecastAdapter.differ.submitList(it.list)
                                forecastRv.apply {
                                    adapter = forecastAdapter
                                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ForecastResponseApi>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })

            //BlueView ucun istifade
            /* var radius = 10f
             val decorView = window.decorView
             val rootView = (decorView.findViewById(android.R.id.content) as ViewGroup?)
             val windowBackground = decorView.background

             rootView?.let {
                 blueView.setupWith(it,RenderScriptBlur(requireContext()),windowBackground,radius)
                 blueView.outlineProvider = ViewOutlineProvider.BACKGROUND
                 blueView.clipToOutline = true
             } */

        }
    }



    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 19
    }

    /*
    private fun setEffectRainSnow(icon : String)  {
         when(icon.dropLast(1)){
            "01" ->{
                initWeatherView(PrecipType.CLEAR)

            }

            "02","03","04" ->{
                initWeatherView(PrecipType.CLEAR)

            }

            "09","10","11" ->{
                initWeatherView(PrecipType.CLEAR)

            }

            "13" ->{
                initWeatherView(PrecipType.CLEAR)

            }

            "50" ->{
                initWeatherView(PrecipType.CLEAR)

            }
        }
    }*/

    /*
        implementation("com.github.MatteoBattilana:WeatherView:3.0.0") bu gradle background Image ucun istifade

     private fun setDynamicallyWallpaper(icon : String) : Int {
          return when(icon.dropLast(1)){
              "01" ->{
                  initWeatherView(PrecipType.CLEAR)
                  R.drawable.snow_bg
              }

              "02","03","04" ->{
                  initWeatherView(PrecipType.CLEAR)
                  R.drawable.cloudy_bg
              }

              "09","10","11" ->{
                  initWeatherView(PrecipType.CLEAR)
                  R.drawable.rainy_bg
              }

              "13" ->{
                  initWeatherView(PrecipType.CLEAR)
                  R.drawable.snow_bg
              }

              "50" ->{
                  initWeatherView(PrecipType.CLEAR)
                  R.drawable.haze_bg
              }
              else ->0
          }
      }

      private fun initWeatherView(type: PrecipType){
          binding.main.apply {
              setWeatherData(type)
              angle = -20
              emissionRate = 100.0f
          }
      }*/

}