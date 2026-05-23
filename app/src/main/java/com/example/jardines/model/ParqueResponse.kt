package com.example.jardines.model

import com.google.gson.annotations.SerializedName

data class ParqueResponse(
    @SerializedName("equipamiento")
    val equipamiento: List<Parque> = emptyList()
)

