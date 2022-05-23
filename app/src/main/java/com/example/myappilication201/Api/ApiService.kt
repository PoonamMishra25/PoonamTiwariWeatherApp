package com.example.mvvmdemo.Api


import com.example.myappilication201.model.ListForecastModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val KEY = "8615ba16e821a83f3e08553ddf32e72d"
const val City = "London"

interface ApiService {
    // https://api.openweathermap.org/data/2.5/forecast?q=london&appid=86323d14b3d20849746feb92032199c0

    @GET("data/2.5/forecast?appid=86323d14b3d20849746feb92032199c0")
    suspend fun getWeatherReportForecast(
        @Query("q") cityName: String?
    ): Response<ListForecastModel>


    companion object {
        private var instance: ApiService? = null
        fun getApiService(): ApiService {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            return instance!!
        }
    }

}
