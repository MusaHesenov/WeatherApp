package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.databinding.WeatherRvBinding
import com.example.weatherapp.model.CurrentResponseApi
import com.example.weatherapp.model.ForecastResponseApi
import kotlin.math.roundToInt

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: WeatherRvBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bin(currentResponseApi: CurrentResponseApi?) {
                binding.apply {
                    currentResponseApi?.let {
                        temperature.text = currentResponseApi.main?.temp?.roundToInt().toString()+ "°"
                        high.text = "H: "+currentResponseApi.main?.tempMax?.roundToInt().toString()+ "°"
                        low.text = "H: "+currentResponseApi.main?.tempMin?.roundToInt().toString()+ "°"
                        statusTxt.text = currentResponseApi.weather?.get(0)?.main.toString()
                        location.text = currentResponseApi.name.toString()
                       // Glide.with(itemView).load(currentResponseApi.icon?.get(0)?.icon).into(weatherIcon)
                    }
                }
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            WeatherRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val differCallback = object : DiffUtil.ItemCallback<CurrentResponseApi>() {
        override fun areItemsTheSame(
            oldItem: CurrentResponseApi,
            newItem: CurrentResponseApi
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CurrentResponseApi,
            newItem: CurrentResponseApi
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentResponseApi = differ.currentList[position]
        holder.bin(currentResponseApi)
    }

    override fun getItemCount() = differ.currentList.size

}