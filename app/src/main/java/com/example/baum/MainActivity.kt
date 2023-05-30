package com.example.baum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.acme.rentmyride.entity.FahrzeugDTO
import com.example.baum.service.FahrzeugService
import com.example.test.entity.Fahrzeug
import com.example.test.entity.KategorieTyp
import java.math.BigDecimal
import java.util.UUID

const val BASE_URL = "http://172.26.32.1:8080/"       // Powershell -> ipconfig -> IPv4 Address koppieren z.B: 172.26.32.1

class MainActivity : AppCompatActivity() {

    private val service: FahrzeugService = FahrzeugService()
    private lateinit var findAllFahrzeugeButton: Button
    private lateinit var findFahrzeugByIdButton: Button
    private lateinit var deleteFahrzeugeButton: Button
    private lateinit var addFahrzeugButton: Button
    private lateinit var ergebnisText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findAllFahrzeugeButton = findViewById(R.id.buttonFindAllFahrzeuge)
        findFahrzeugByIdButton = findViewById(R.id.buttonFindFahrzeugById)
        deleteFahrzeugeButton = findViewById(R.id.buttonDeleteFahrzeugById)
        addFahrzeugButton = findViewById(R.id.buttonAddFahrzeug)
        ergebnisText = findViewById(R.id.textErgebnis)


        findAllFahrzeugeButton.setOnClickListener {
            service.getFahrzeuge(ergebnisText)
        }

        findFahrzeugByIdButton.setOnClickListener {
            val uuid = "00000000-0000-0000-0000-000000000000"
            service.getFahrzeugById(ergebnisText, uuid)
        }

        deleteFahrzeugeButton.setOnClickListener {
            val uuid = "00000000-0000-0000-0000-000000000001"
            service.deleteFahrzeug(ergebnisText, uuid)
        }

        addFahrzeugButton.setOnClickListener {

            val fahrzeug = FahrzeugDTO(
                beschreibung = "Auto1",
                kategorie = "P",
                anzahlTueren = 3,
                anzahlSitze = 4,
                hatKlimaanlage = true,
                preisProTag = BigDecimal(12),
                bestizer_fk = 1,
                bild = "kein Bild vorhanden",
            )

            service.addFahrzeug(ergebnisText, fahrzeug)
        }

    }
}