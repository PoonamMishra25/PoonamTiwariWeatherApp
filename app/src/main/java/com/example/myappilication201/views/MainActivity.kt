package com.example.myappilication201.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappilication201.Communicator
import com.example.myappilication201.R

class MainActivity : AppCompatActivity() ,Communicator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cityFragment=CityFragment()
        supportFragmentManager.beginTransaction().replace(R.id.containerView,cityFragment).commit()
    }


    override fun passData(cityName: String, flag: Int) {
        val bundle=Bundle()
        bundle.putString("KEY",cityName)
        bundle.putInt("flag",flag)
        val transaction=this.supportFragmentManager.beginTransaction()
        val weatherFragment=WeatherFragment()
        weatherFragment.arguments=bundle
        transaction.replace(R.id.containerView,weatherFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}