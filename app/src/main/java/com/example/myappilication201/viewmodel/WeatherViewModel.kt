package com.example.myappilication201.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myappilication201.repository.WeatherRepositoryImpl
import com.example.myappilication201.model.*
import com.example.myappilication201.views.WeatherFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// androidx == Jetpack Libraries
class WeatherViewModel(private val repositoryImpl: WeatherRepositoryImpl) : ViewModel() {


    // this block is called when the class is first created
    init {
        getWeatherReport(WeatherFragment.str)
    }

    // LiveData -> is a wrapper class for observable data
    // is also lifecycle aware
    // private MutableLiveData is updated by the ViewModel
    private val _weatherReport = MutableLiveData<List<ForecastModel>>()

    // public LiveData will be observed by the View Layer
    val weatherReport: LiveData<List<ForecastModel> >get() = _weatherReport


    fun getWeatherReport(cityName:String?) {
       CoroutineScope(Dispatchers.Main).launch {
            val response = repositoryImpl.getWeatherReportForecast(cityName)
            _weatherReport.postValue(response.results)

        }
    }


}