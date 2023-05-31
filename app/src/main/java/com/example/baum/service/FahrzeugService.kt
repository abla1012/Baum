package com.example.baum.service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.acme.rentmyride.entity.FahrzeugDTO
import com.example.baum.BASE_URL
import com.example.test.entity.Fahrzeug
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.Exception

//Hier wird das APIInterface benutzt
class FahrzeugService : AppCompatActivity() {

    fun getFahrzeugById(ergText: TextView, ergBild: ImageView, id: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.getFahrzeug(id)

        retrofitData.enqueue(object : Callback<Fahrzeug?> {
            override fun onResponse(call: Call<Fahrzeug?>, response: Response<Fahrzeug?>) {
                val responseBody = response?.body()

                Log.d("MainActivity", responseBody.toString())

                val myStringBuilder = StringBuilder()
                if (responseBody != null) {
                    myStringBuilder.append("Mein Fahrzeug hat die Fahrzeugnummer ${responseBody.fahrzeugnummer}")

                    ergBild.setImageBitmap(decodedString(responseBody.bild))
                } else {
                    myStringBuilder.append("UFFPASSE! Kein Fahrzeug mit dieser Id vorhande!")
                }

                ergText.text = myStringBuilder
            }

            override fun onFailure(call: Call<Fahrzeug?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    private fun decodedString(base64String: String): Bitmap? {

        var imageBytes: ByteArray
        var decodedImage:  Bitmap

        try {
            imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e: Exception)
        {
            Log.d("MainActivity", "decodedStringFailed: "+e.message)

            val path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            )
            val file = File(path, "imagenotfound.jpg")
            val bildpfad = file.absolutePath

            Log.d("MainActivity", "bildpfad: "+bildpfad)

            val bild = bildToString(bildpfad)
            imageBytes = Base64.decode(bild, Base64.DEFAULT)
            decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        return decodedImage
    }

    fun getFahrzeuge(ergText: TextView) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.getFahrzeuge()

        retrofitData.enqueue(object : Callback<List<Fahrzeug>?> {
            override fun onResponse(
                call: Call<List<Fahrzeug>?>,
                response: Response<List<Fahrzeug>?>
            ) {
                val responseBody = response.body()!!

                Log.d("MainActivity", responseBody.toString())

                val myStringBuilder = StringBuilder()
                for (myFahrzeuge in responseBody) {
                    myStringBuilder.append(myFahrzeuge.fahrzeugnummer)
                    myStringBuilder.append("\n")
                }

                ergText.text = myStringBuilder
            }

            override fun onFailure(call: Call<List<Fahrzeug>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    fun addFahrzeug(ergText: TextView, fahrzeug: FahrzeugDTO) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.addFahrzeuge(fahrzeug)

        Log.d("MainActivity", retrofitBuilder.addFahrzeuge(fahrzeug).request().toString())

        retrofitData.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                val responseBody = response.body()!!

                val myStringBuilder = StringBuilder()
                if (responseBody != null) {
                    myStringBuilder.append("Das neue Fahrzeug hat die Fahrzeugnummer: $responseBody!")
                } else {
                    myStringBuilder.append("Fahrzeug konnte nicht angelegt werden!")
                }
                ergText.text = myStringBuilder

                Log.d("MainActivity", "Fahrzeug wurde erfolgreich hinzugefügt!")
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    fun deleteFahrzeug(ergText: TextView, id: String) {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.deleteFahrzeug(id)

        retrofitData.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                val responseCode = response.code()

                Log.d("MainActivity", "ResponseCode = $responseCode")

                val myStringBuilder = StringBuilder()
                if (responseCode == 204) {
                    myStringBuilder.append("Das Fahrzeug mit der id $id wurde gelöscht!")
                } else {
                    myStringBuilder.append("UFFPASSE! Kein Fahrzeug mit dieser id $id gefunden!")
                }

                ergText.text = myStringBuilder
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    fun bildToString(bildpfad: String) : String {

        val fileContent: ByteArray = File(bildpfad).readBytes()
        val encodedString = java.util.Base64.getEncoder().encodeToString(fileContent)

        return encodedString
    }
}
