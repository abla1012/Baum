package com.example.baum

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.acme.rentmyride.entity.FahrzeugDTO
import com.example.baum.service.FahrzeugService
import com.example.baum.service.LoginService
import java.io.File
import java.math.BigDecimal
import java.util.Base64


const val BASE_URL = "http://172.26.32.1:8080/"       // Powershell -> ipconfig -> IPv4 Address koppieren z.B: 172.26.32.1

class MainActivity : AppCompatActivity() {

    private val service: FahrzeugService = FahrzeugService()

    private val loginService : LoginService = LoginService()

    private lateinit var findAllFahrzeugeButton: Button
    private lateinit var findFahrzeugByIdButton: Button
    private lateinit var deleteFahrzeugeButton: Button
    private lateinit var addFahrzeugButton: Button

    private lateinit var loginToBackend : Button

    private lateinit var ergebnisText: TextView
    private lateinit var ergebnisBild: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findAllFahrzeugeButton = findViewById(R.id.buttonFindAllFahrzeuge)
        findFahrzeugByIdButton = findViewById(R.id.buttonFindFahrzeugById)
        deleteFahrzeugeButton = findViewById(R.id.buttonDeleteFahrzeugById)
        addFahrzeugButton = findViewById(R.id.buttonAddFahrzeug)

        loginToBackend = findViewById(R.id.buttonLoginToBackend)

        ergebnisText = findViewById(R.id.textErgebnis)
        ergebnisBild = findViewById(R.id.bildErgebnis)

        findAllFahrzeugeButton.setOnClickListener {
            service.getFahrzeuge(ergebnisText)
        }

        findFahrzeugByIdButton.setOnClickListener {
            val uuid = "00000000-0000-0000-0000-000000000000"
            service.getFahrzeugById(ergebnisText, ergebnisBild, uuid)
        }

        deleteFahrzeugeButton.setOnClickListener {
            val uuid = "00000000-0000-0000-0000-000000000001"
            service.deleteFahrzeug(ergebnisText, uuid)
        }

        addFahrzeugButton.setOnClickListener {

            var bildpfad: String

            /* For Android 11 or above use the code below on the onCreate() of the activity. It will run once and ask for permission.
            if (Build.VERSION.SDK_INT >= 30) {
                if (!Environment.isExternalStorageManager()) {
                    val getpermission = Intent()
                    getpermission.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                    startActivity(getpermission)
                }
            }
             */

            val path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            )
            val file = File(path, "porsche.jpg")
            bildpfad = file.absolutePath

            var bild : String
            try {
                bild = service.bildToString(bildpfad)
            } catch(e: Exception)
            {
                bild = "bildToSting Fehlgeschlagen"
                Log.d("MainActivity", "bildToStingFailed: "+e.message)
            }

            val fahrzeug = FahrzeugDTO(
                beschreibung = "Auto1",
                kategorie = "P",
                anzahlTueren = 3,
                anzahlSitze = 4,
                hatKlimaanlage = true,
                preisProTag = BigDecimal(12),
                bestizer_fk = 1,
                bild = bild,
            )

            service.addFahrzeug(ergebnisText, fahrzeug)
        }

        loginToBackend.setOnClickListener {
            val name = "admin"
            val passwort = "p"
            loginService.loginToBackend(ergebnisText, name, passwort)
        }
    }

}