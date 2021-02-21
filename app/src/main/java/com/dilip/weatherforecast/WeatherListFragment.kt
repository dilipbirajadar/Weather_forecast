package com.dilip.weatherforecast

import Lista
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dilip.weatherforecast.adapter.ForecastAdapter
import com.dilip.weatherforecast.data.api.ApiHelperImpl
import com.dilip.weatherforecast.data.api.RetrofitBuilder
import com.dilip.weatherforecast.util.Status
import com.dilip.weatherforecast.util.ViewModelFactory
import com.dilip.weatherforecast.viewmodels.ForecastListViewModel

class WeatherListFragment : Fragment() {

    private lateinit var forecastAdapter: ForecastAdapter
    private lateinit var forecastViewModel: ForecastListViewModel
    private lateinit var recyclerView :RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast_five_days, container, false)
        setupViewModel()
        setObserver()

        recyclerView = view.findViewById(R.id.weatherList)
        val context = view.context


            recyclerView.apply {
                // Set LinearLayoutManager using default vertical list:
                layoutManager = LinearLayoutManager(context)

                //hasFixedSize() // Improve performance (use only with fixed size items)
                setItemViewCacheSize(20)

                // Set adapter and initialise with empty list:
                forecastAdapter = ForecastAdapter(mutableListOf(),activity)
                adapter = forecastAdapter



        }

        return view
    }

    private fun setObserver() {

        forecastViewModel.getForeCast().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.e("forecast response is:", it.toString())
                    it.data?.list

                    recyclerView.apply {
                        // Set LinearLayoutManager using default vertical list:
                        layoutManager = LinearLayoutManager(context)

                        //hasFixedSize() // Improve performance (use only with fixed size items)
                        setItemViewCacheSize(20)

                        // Set adapter and initialise with empty list:
                        forecastAdapter = ForecastAdapter(it.data?.list as MutableList<Lista>,activity)
                        adapter = forecastAdapter


                    }

                }

                Status.LOADING -> {
                    //progressBar.visibility = View.VISIBLE
                    //recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    Log.e("error is:", it.toString())
                }
            }
        })
    }

    private fun itemClicked(item: Lista) {

    }

    private fun setupViewModel() {
        forecastViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(
                        ApiHelperImpl(RetrofitBuilder.apiService),
                        //DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
                )
        ).get(ForecastListViewModel::class.java)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }
}