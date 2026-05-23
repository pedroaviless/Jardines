package com.example.jardines.model

import com.google.gson.annotations.SerializedName

data class Parque(
    val id: Int = 0,

    @SerializedName("title")
    val titulo: String = "",

    val calle: String = "",

    val imagen : String ="",

    val lastUpdated : String = ""
)