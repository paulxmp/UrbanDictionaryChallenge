package com.nomadconsultants.urbandictionarychallenge.service

import com.nomadconsultants.urbandictionarychallenge.model.UrbanDictionaryDefinitions
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UrbanDictionaryDefinitionsService {

    @GET("define")
    fun getDefinitions(@Query("term") term: String): Single<UrbanDictionaryDefinitions>
}