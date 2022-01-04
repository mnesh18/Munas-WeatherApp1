package com.example.munasweatherapp1

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.munasweatherapp1.model.Weather
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    val apiKey = "2ae020d1cd8387502e092c6234c2d29d"
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.searchButton)

        button.setOnClickListener {
            getWeather()
            val searchInput = findViewById<EditText>(R.id.searchInput).text.clear()
        }
    }

    private fun getWeather(){
        val searchInput = findViewById<EditText>(R.id.searchInput)
        try {
            if (searchInput.text.isNotBlank()) {
                var fullURL =
                    "https://api.openweathermap.org/data/2.5/weather?q=${searchInput.text}&units=metric&appid=$apiKey"
                var queue = Volley.newRequestQueue(this)
                var stringRequest = StringRequest(
                    Request.Method.POST, fullURL, { response ->
                        var results = gson.fromJson(response, Weather::class.java)
                        findViewById<TextView>(R.id.temp).text = results.main.temp.toInt().toString() + " ºc"
                        findViewById<TextView>(R.id.status).text = results.weather[0].main
                        findViewById<TextView>(R.id.address).text = results.name + ", " + results.sys.country
                        findViewById<TextView>(R.id.temp_min).text = "Min Temp: " + results.main.temp_min + " ºC"
                        findViewById<TextView>(R.id.temp_max).text = "Max Temp: " + results.main.temp_max + " ºC"



                    }, {
                        Toast.makeText(this, "Something Wrong", Toast.LENGTH_SHORT).show()
                        println(it.message)
                    }
                )
                queue.add(stringRequest)
            } else{
                Toast.makeText(this, "Write Something!", Toast.LENGTH_SHORT).show()

            }
        }catch (err:Exception){

        }
    }
}