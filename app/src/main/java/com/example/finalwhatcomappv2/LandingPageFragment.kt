package com.example.finalwhatcomappv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finalwhatcomappv2.databinding.FragmentLandingPageBinding
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class LandingPageFragment : Fragment() {

    private var _binding: FragmentLandingPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit private var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLandingPageBinding.inflate(inflater, container, false)
        return binding.root
        return inflater.inflate(R.layout.fragment_landing_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var serviceTypeChoice: String = "FoodBanks"
        val serviceTypesList: MutableList<String> = ArrayList()
        jsonParseClient(serviceTypesList, "https://radiant-dawn-48071.herokuapp.com/typesOfService")
        binding.serviceTypeSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                serviceTypeChoice = parent?.getItemAtPosition(position).toString()
            }

        }
        binding.landingButton.setOnClickListener {
            val action = LandingPageFragmentDirections.
            actionLandingPageFragmentToCacheView(serviceTypeChoice)
            findNavController().navigate(action)
        }

    }

    fun jsonParseClient (serviceList: MutableList<String>, url: String) {
        println("Attempting to parse JSON!")
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: okhttp3.Call, response: Response) {
                println("Parsing data from $url")
                val strResponse = response.body?.string()
                val jsonContact = JSONObject(strResponse)
                val jsonObj: JSONObject = jsonContact
                val jsonKeys: Iterator<String> = jsonObj.keys()
                while (jsonKeys.hasNext()) {
                    val serviceType: String = jsonObj.getString(jsonKeys.next())
                    println(serviceType)
                    serviceList.add(serviceType)
                    println("JSON parse successful!")
                }
                activity!!.runOnUiThread {
                    val spinnerAdapter = ArrayAdapter<String>(requireActivity().applicationContext, android.R.layout.simple_spinner_dropdown_item, serviceList)
                    binding.serviceTypeSpinner.adapter = spinnerAdapter
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                val message = "The application could not contact the server."
                val toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }

}