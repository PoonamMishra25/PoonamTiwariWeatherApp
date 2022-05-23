package com.example.myappilication201.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappilication201.repository.WeatherRepositoryImpl
import com.example.mvvmexample.view.WeatherRecyclerAdapter
import com.example.myappilication201.viewmodel.WeatherViewModel
import com.example.myappilication201.R
import com.example.myappilication201.databinding.FragmentWeatherBinding
import com.example.myappilication201.model.ForecastModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentWeatherBinding? = null
    private val binding: FragmentWeatherBinding get() = _binding!!



  private lateinit var weatherAdapter:WeatherRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val viewModel: WeatherViewModel by lazy {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel(WeatherRepositoryImpl()) as T
            }
        }.create(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(layoutInflater)
         str=arguments?.getString("KEY")
        flag=arguments?.getInt("flag")
        binding.cityNameFrag.text = str+"'s Hourly Weather Report"
        configureObserver()

        binding.imageButton.setOnClickListener { getFragmentManager()?.popBackStack() }
        return binding.root
    }

    private fun configureObserver() {
        weatherAdapter = WeatherRecyclerAdapter(openDetails = ::openDetails)
        // viewLifecycleOwner -> points to the owner of the ViewModel
        viewModel.weatherReport.observe(viewLifecycleOwner) { response ->
            if (response.isEmpty()) {
                binding.tvErrorTextView.text = "Network call failed"
            } else {
                weatherAdapter.setUserList(response)
                binding.apply {
                    rvList.layoutManager=LinearLayoutManager(requireContext())
                    rvList.adapter = weatherAdapter
                    pbLoading.visibility = View.GONE
                    tvErrorTextView.visibility = View.GONE
                }
            }
        }
    }

    private fun openDetails(weatherReport: ForecastModel) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.containerView, DetailedWeatherFragment.newInstance(weatherReport))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        var flag:Int?=null
        var str:String?=null

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}