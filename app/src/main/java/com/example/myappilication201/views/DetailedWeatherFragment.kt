package com.example.myappilication201.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.myappilication201.R
import com.example.myappilication201.databinding.FragmentDetailedWeatherBinding
import com.example.myappilication201.model.ForecastModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailedWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailedWeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentDetailedWeatherBinding? = null
    private val binding: FragmentDetailedWeatherBinding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detailed_weather, container, false)
        _binding = FragmentDetailedWeatherBinding.inflate(layoutInflater)
        val forecastModel: ForecastModel? = arguments?.getParcelable(ARG_PARAM1)



        binding.tvDetailedDesc.text = forecastModel?.weather?.get(0)?.description.toString()
        binding.tvHumidity.text = forecastModel?.main?.humidity.toString()+"%"
        binding.tvPressure.text = forecastModel?.main?.pressure.toString()+"hPa"
        binding.tvWind.text = forecastModel?.wind?.speed.toString()+"Km/h"


        when(WeatherFragment.flag){
                    0-> {
                        binding.tvDetailedTemp.text = forecastModel?.main?.temp.toString()+"K"
                        binding.tvFeelsLike.text = forecastModel?.main?.feels_like.toString()+"K"
                        binding.tvMaxTemp.text = forecastModel?.main?.temp_max.toString()+"K"
                        binding.tvMinTemp.text = forecastModel?.main?.temp_min.toString()+"K"
                    }
            1->{
                binding.tvDetailedTemp.text = forecastModel?.main?.temp?.let { kelvinToCelsius(it) }.toString().substring(0,4)+"C"
                binding.tvFeelsLike.text = forecastModel?.main?.feels_like?.let { kelvinToCelsius(it) }.toString().substring(0,4)+"C"
                binding.tvMaxTemp.text = forecastModel?.main?.temp_max?.let { kelvinToCelsius(it) }.toString().substring(0,4)+"C"
                binding.tvMinTemp.text = forecastModel?.main?.temp_min?.let { kelvinToCelsius(it) }.toString().substring(0,4)+"C"
            }

            2->{
                binding.tvDetailedTemp.text = forecastModel?.main?.temp?.let { kelvinToFehrenheit(it) }.toString().substring(0,4)+"F"
                binding.tvFeelsLike.text = forecastModel?.main?.feels_like?.let { kelvinToFehrenheit(it) }.toString().substring(0,4)+"F"
                binding.tvMaxTemp.text = forecastModel?.main?.temp_max?.let { kelvinToFehrenheit(it) }.toString().substring(0,4)+"F"
                binding.tvMinTemp.text = forecastModel?.main?.temp_min?.let { kelvinToFehrenheit(it) }.toString().substring(0,4)+"F"
            }

        }
        binding.imageView.setOnClickListener { getFragmentManager()?.popBackStack() }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailedWeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(weatherReport:ForecastModel) =
            DetailedWeatherFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, weatherReport)


                }
            }

        fun kelvinToCelsius(temp:Double):Double{
            return temp - 273.15
        }

       // val fahrenheit:Double=1.8*(forecastModel?.main?.temp-273)+32

        fun kelvinToFehrenheit(temp:Double):Double{
            return 1.8*(temp-273)+32
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}