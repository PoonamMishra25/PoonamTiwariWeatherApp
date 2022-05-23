package com.example.myappilication201.repository

import com.example.mvvmdemo.Api.ApiService
import com.example.myappilication201.model.*

// makes calls to the data source
// contract
interface WeatherRepository {
    suspend fun getWeatherReportForecast(cityName: String?): ListForecastModel

}

class WeatherRepositoryImpl(
    private val service: ApiService = ApiService.getApiService()
    ): WeatherRepository {



   override suspend fun getWeatherReportForecast(cityName: String?): ListForecastModel {
        val response=service.getWeatherReportForecast(cityName = cityName)
        return if(response.isSuccessful){
            response.body()!!
        }else{
            ListForecastModel(emptyList())
        }
    }




}
