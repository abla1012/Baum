package com.example.baum.service

import android.util.Log
import android.widget.TextView
import com.acme.rentmyride.entity.FahrzeugDTO
import com.example.baum.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginService {

    fun loginToBackend(ergText: TextView, name:String, passwort:String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.loginToBackend(name, passwort)

        Log.d("MainActivity", retrofitBuilder.loginToBackend(name, passwort).request().toString())

        retrofitData.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val responseCode = response.code()

                val myStringBuilder = StringBuilder()
                if (responseCode == 200) {
                    myStringBuilder.append("Erfolgreich eingeloogt!")
                } else {
                    myStringBuilder.append("UFFPASSE! Login fehlgeschlagen!!")
                }

                ergText.text = myStringBuilder
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }
}