package com.example.test.entity

import java.math.BigDecimal
import java.util.UUID

data class Fahrzeug(
    val fahrzeugnummer: UUID,
    val beschreibung: String,
    val kategorie: KategorieTyp,
    val anzahlTueren: Int,
    val hatKlimaanlage: Boolean,
    val anzahlSitze: Int,
    val preisProTag: BigDecimal,
    val bestizer_fk: Int,
    val bild: String,
)