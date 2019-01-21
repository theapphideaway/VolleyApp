package com.theapphideaway.volleyapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley.newRequestQueue
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import org.json.JSONObject
import java.util.*
import android.R.attr.country




class MainActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue?= null
    val stringLink = "https://www.magadistudio.com" +
            "/complete-android-developer-course-source-files/string.html"
    var anotherWeatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=London&units=imperial&appid=c6afdab60aa89481e297e0a4f19af055"

    var weather = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID={3d62163d2295020377c4d7b90460b3f4}"
    var testLink = "https://jsonplaceholder.typicode.com/posts"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyRequest = newRequestQueue(this)

        fetchJSONOkHttp(anotherWeatherUrl)

    }


    fun fetchJSONOkHttp(Url: String){
        println("Attempting to fetch JSON")



        val request = Request.Builder()
            .url(Url).build()


        val client = OkHttpClient()
//
//        client.interceptors().add(Interceptor { chain ->
//            val original = chain.request()
//            val request = original.newBuilder()
//                .header("appid", "c6afdab60aa89481e297e0a4f19af055")
//                .method(original.method(), original.body())
//                .build()
//            chain.proceed(request)
//        })

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val body = response.body()?.string()
                println(body)

               // val gson = GsonBuilder().create()

               //val currentTempObject = gson.fromJson(body, Weather::class.java)

                var reader = JSONObject(body)


                var main = reader.getJSONObject("main")

                var currentTemp = main.getInt("temp")

                var tempMin = main.getInt("temp_min")

                var tempMax = main.getInt("temp_max")

                //val temp = gson.fromJson(body, Weather::class.java)

                println(currentTemp)

                runOnUiThread { text_view.text = currentTemp.toString() }
            }
        })
    }


//    fun getJsonObject(Url: String){
//
//
//        val accessTokenRequest: JsonObjectRequest = object : JsonObjectRequest(
//            com.android.volley.Request.Method.GET, Url, JSONObject(),
//            Response.Listener {
//                    response: JSONObject? ->
//                try {
//                    println(response.toString())
//
//                }catch (e: JSONException){
//                    e.printStackTrace()
//                }
//            }, Response.ErrorListener {
//                    error: VolleyError ->
//                try {
//
//                    println(error.toString())
//
//                }catch (e: JSONException){
//                    e.printStackTrace()
//                }
//            }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                var params: MutableMap<String, String>? = super.getHeaders()
//                if (params == null) params = HashMap()
//                params["appid"] = "3d62163d2295020377c4d7b90460b3f4"
//                params["q"] = "Tampa"
//                return params
//            }
//
//        }
//        volleyRequest!!.add(accessTokenRequest)
//    }
//
//
//    fun getJsonArray(Url:String){
//        val jsonArray = JsonArrayRequest(com.android.volley.Request.Method.GET, Url,
//            Response.Listener {
//                    response: JSONArray? ->
//                try {
//
//                    println(response.toString())
//
//                }catch (e: JSONException){
//                    e.printStackTrace()
//                }
//            },
//            Response.ErrorListener {
//                    error: VolleyError ->
//                try {
//
//                    println(error.toString())
//
//                }catch (e: JSONException){
//                    e.printStackTrace()
//                }
//            })
//        volleyRequest!!.add(jsonArray)
//    }
//
//    fun getString(Url: String){
//        val stringRequest = StringRequest(com.android.volley.Request.Method.GET, Url,
//            Response.Listener {
//                response: String? ->
//                try {
//
//                    println(response.toString())
//
//                }catch (e: JSONException){
//                    e.printStackTrace()
//                }
//            },
//        Response.ErrorListener {
//            error: VolleyError ->
//            try {
//
//                println(error.toString())
//
//            }catch (e: JSONException){
//                e.printStackTrace()
//            }
//        })
//
//        volleyRequest!!.add(stringRequest)
//    }
}

data class Weather<T>(
    @SerializedName("weather") val main: Array<T>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Weather<*>

        if (!main.contentEquals(other.main)) return false

        return true
    }

    override fun hashCode(): Int {
        return main.contentHashCode()
    }
}

data class Temp(
    @SerializedName("temp") val temp: String
)
