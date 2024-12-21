package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    //https://api.openweathermap.org/data/2.5/weather?q={cityName}&appid={API_KEY}
    private val API_KEY="you_api_key_of_openweathermap"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        fetchWeatherData("Aligarh")
        searchCity()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun searchCity() {
        val searchView=binding.searchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun fetchWeatherData(cityName:String) {

        var retrofit=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)

        val response= retrofit.getWeather(cityName,API_KEY,"metric")

        response.enqueue(object: Callback<ApiData>{
            override fun onResponse(call: Call<ApiData>, response: Response<ApiData>) {
                val responseBody=response.body()
//                Log.d("response", "onResponse: "+response.body().toString())

                if (responseBody!=null){
                    val temperature=responseBody.main.temp
                    val humidity=responseBody.main.humidity
                    val windSpeed=responseBody.wind.speed
                    val sunrise=responseBody.sys.sunrise.toLong()
                    val sunset=responseBody.sys.sunset.toLong()
                    val min=responseBody.main.temp_min
                    val max=responseBody.main.temp_max
                    val sea=responseBody.main.sea_level
                    val weather=responseBody.weather.firstOrNull()?.main?:"Unknown"
//                    Log.d("TAG ", "onResponse: $temperature")
                    binding.textView5.text="$temperature °C"
                    binding.textView6.text="Min temp: $min °C"
                    binding.textView7.text="Max temp: $max °C"
                    binding.textView8.text="$weather"
                    binding.rise.text="${time(sunrise)}"
                    binding.set.text="${time(sunset)}"
                    binding.speed.text="$windSpeed m/s"
                    binding.humidity.text="$humidity %"
                    binding.sea.text="$sea hPa"
                    binding.condi.text="$weather"
                    binding.textView10.text=getDay(System.currentTimeMillis()) //day
                    binding.textView11.text= date()//date
                    binding.textView3.text="${responseBody.name}"
                    changeRootImage(weather)

                }
            }

            override fun onFailure(call: Call<ApiData>, t: Throwable) {
                Log.d("Fail", "onFailure: "+t.message)
            }

        })
    }

    private fun changeRootImage(weather: String) {
        when(weather){
            "Haze","Partly Clouds","Clouds","Overcast","Mist","Foggy"->{
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud)
            }
            "Light Rain","Drizzle","Moderate Ra","Showers","Heavy Rain"->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }
            "Clear Sky","Sunny","Clear"->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
            "Light Snow","Moderate Snow","Heavy Snow","Blizzard"->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }
            else->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
        }
        binding.lottieAnimationView.playAnimation()
    }

    private fun date(): String {
        val date=SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return date.format((Date()))
    }

     private fun time(time: Long): String {
        val date=SimpleDateFormat("HH:mm", Locale.getDefault())
        return date.format((Date(time*1000)))
    }

    fun getDay(day:Long):String{
        val date=SimpleDateFormat("EEEE", Locale.getDefault())
        return date.format((Date()))
    }
}
