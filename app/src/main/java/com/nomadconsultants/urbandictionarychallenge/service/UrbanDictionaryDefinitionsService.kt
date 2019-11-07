package com.nomadconsultants.urbandictionarychallenge.service

import com.nomadconsultants.urbandictionarychallenge.model.UrbanDictionaryDefinitions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UrbanDictionaryDefinitionsService {

    @GET("define")
    fun getDefinitions(@Query("term") term: String): Call<UrbanDictionaryDefinitions>
}