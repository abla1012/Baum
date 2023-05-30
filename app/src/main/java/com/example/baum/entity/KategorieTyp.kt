package com.example.test.entity

enum class KategorieTyp(val value : String) {
    PKW("P"),
    NUTZFAHRZEUG("N");

    //@JsonValue
    override fun toString() = value
}