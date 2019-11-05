package com.nomadconsultants.urbandictionarychallenge.service

import com.nomadconsultants.urbandictionarychallenge.model.Definitions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DefinitionsService {

    @GET("define")
    fun getDefinitions(@Query("term") term: String): Call<Definitions>
}