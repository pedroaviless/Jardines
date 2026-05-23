package com.example.jardines.network

import com.example.jardines.model.ParqueResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JardinesApiService {

    @GET("sede/servicio/equipamiento/category/820.json")
    suspend fun  getParques(
       @Query("rows") rows: Int = 10000
    ): ParqueResponse
}