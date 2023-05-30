package com.acme.rentmyride.entity

import com.example.test.entity.KategorieTyp
import java.math.BigDecimal

data class FahrzeugDTO(
    val beschreibung: String,
    val bild: String,
    val kategorie: String,
    val anzahlTueren: Int,
    val hatKlimaanlage: Boolean,
    val anzahlSitze: Int,
    val preisProTag: BigDecimal,
    val bestizer_fk: Int,
)