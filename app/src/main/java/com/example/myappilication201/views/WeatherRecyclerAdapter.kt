package com.example.mvvmexample.view

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myappilication201.databinding.WeatherListItemBinding
import com.example.myappilication201.model.ForecastModel
import com.example.myappilication201.views.DetailedWeatherFragment
import com.example.myappilication201.views.WeatherFragment
import java.time.format.DateTimeFormatter

// Unit -> void
// Unit just returns the function call
// Nothing != Unit
class WeatherRecyclerAdapter(
    private val userList: MutableList<ForecastModel> = mutableListOf(),
    private val openDetails: (ForecastModel) -> Unit
    ): RecyclerView.Adapter<WeatherRecyclerAdapter.UserViewHolder>() {

    fun setUserList(newList: List<ForecastModel>) {
        userList.clear()
        userList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(
        private val binding: WeatherListItemBinding
        ): RecyclerView.ViewHolder(binding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(forecastModel: ForecastModel) {


                Glide.with(binding.imageCloud)
                    .load("https://openweathermap.org/img/wn/" + forecastModel.weather[0].icon + "@2x.png")
                    .into(binding.imageCloud)
                val timestamp =forecastModel.dt.toLong()
                val timestampAsDateString = DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(timestamp))


                binding.tvTimeRv.text=timestampAsDateString.substring(0,10)
                binding.tvDayRV.text=timestampAsDateString.substring(11,16)
                binding.tvStatus.text= forecastModel.weather[0].description
//Temperature conversions
//                val celsius:Double=forecastModel.main.temp-273.15
//                val fahrenheit:Double=1.8*(forecastModel.main.temp-273)+32

                when(WeatherFragment.flag){

                    0 -> binding.tvCurrentTemp.text= forecastModel.main.temp.toString()+"K"
                    1 -> binding.tvCurrentTemp.text= DetailedWeatherFragment.kelvinToCelsius(forecastModel.main.temp).toString().substring(0,4)+"C"
                    2 -> binding.tvCurrentTemp.text= DetailedWeatherFragment.kelvinToFehrenheit(forecastModel.main.temp).toString().substring(0,4)+"F"

                }


                binding.tvWindRV.text=forecastModel.wind.speed.toString()+"Km/h"
                binding.root.setOnClickListener {
                    openDetails(forecastModel)
                    // this ^ is calling this \/ from the UsersFragment
//                    fun openDetails(WeatherReportModel: WeatherReportModel) {
//                        parentFragmentManager.beginTransaction()
//                            .replace(R.id.fragment_container, DetailsFragment.newInstance(WeatherReportModel))
//                            .addToBackStack(null)
//                            .commit()
//                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            WeatherListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount() = userList.size
}