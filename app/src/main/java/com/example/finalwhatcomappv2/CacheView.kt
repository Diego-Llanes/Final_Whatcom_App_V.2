package com.example.finalwhatcomappv2

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalwhatcomappv2.cache.CacheViewModel
import com.example.finalwhatcomappv2.cache.CacheViewModelFactory
import com.example.finalwhatcomappv2.databinding.CacheViewBinding
import com.example.whatcomapp.cache.CacheDatabase
import com.example.whatcomapp.cache.CacheEntity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class CacheView : Fragment() {

    private var _binding: CacheViewBinding? = null
    lateinit var listViewDetails: ListView
    var arrayListDetails: ArrayList<ServiceData> = ArrayList()
    private val args: CacheViewArgs by navArgs()

    // values for location finding
    private var longitude: String? = null
    private var latitude: String? = null
    private var range: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = CacheViewBinding.inflate(inflater, container, false)
        //insertData()
        listViewDetails = binding.listView as ListView
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        return binding.root
        return inflater.inflate(R.layout.fragment_cache_view, container, false)

    }

    // this method doesn't work yet, need to figure out how to get it to work in a fragment
    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()
                println(latitude)
                println(longitude)
            } else {
                println("Unable to get coordinates!")
            }
        }
    }

    private fun insertData() {
        val name = "jake"
        val addy = "1223113 state"
        val number = "720poopnum,ber"
        val email = "email"
        val website = "website"
        val days = "12"
        val notes = "dont go here"
        val type = "Food Bank"

        val entity = CacheEntity(0,name,addy,number,email,website,days,notes,type)

        val application = requireNotNull(this.activity).application
        val dataSource = CacheDatabase.getInstance(application).CacheDatabaseDao
        val viewModelFactory = CacheViewModelFactory(dataSource, application)
        val mCacheViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CacheViewModel::class.java)

        mCacheViewModel.addEntity(entity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLastKnownLocation()
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_cacheView_to_landingPageFragment)
        }

        binding.floatingActionButton.setOnClickListener {
            val toast = Toast.makeText(activity, "Refreshing", Toast.LENGTH_SHORT)
            toast.show()
            jsonParseClient("https://radiant-dawn-48071.herokuapp.com/service/${args.serviceType}")
        }

    }

    fun jsonParseClient (url: String) {
        println("Attempting to parse JSON!")
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: okhttp3.Call, response: Response) {
                println("Parsing data from $url")
                val strResponse = response.body?.string()
                val jsonContact = JSONObject(strResponse)
                val jsonObj: JSONObject = jsonContact//.getJSONObject("CommunityMeals")
                arrayListDetails = ArrayList()
                val jsonKeys: Iterator<String> = jsonObj.keys()
                while (jsonKeys.hasNext()) {
                    var jsonObjDetail: JSONObject = jsonObj.getJSONObject(jsonKeys.next())
                    var service: ServiceData = ServiceData()
                    service.name = jsonObjDetail.getString("name")
                    service.address = jsonObjDetail.getString("address")
                    service.phone = jsonObjDetail.getString("phone")
                    service.email = jsonObjDetail.getString("email")
                    service.website = jsonObjDetail.getString("website")
                    service.days = jsonObjDetail.getString("days")
                    service.months = jsonObjDetail.getString("months")
                    service.hours = jsonObjDetail.getString("hours")
                    service.additionalNotes = jsonObjDetail.getString("additional notes")
                    arrayListDetails.add(service)
                }

                activity!!.runOnUiThread {
                    listViewDetails.adapter = CustomAdapter(activity!!.applicationContext, arrayListDetails)
                    println("JSON parse successful!")
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println("JSON parse failed!")
            }

        })
    }
}