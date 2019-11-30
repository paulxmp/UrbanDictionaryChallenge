package com.nomadconsultants.urbandictionarychallenge.repository

import com.nomadconsultants.urbandictionarychallenge.model.UrbanDictionaryDefinitions
import com.nomadconsultants.urbandictionarychallenge.service.UrbanDictionaryDefinitionsService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UrbanDictionaryDefinitionsRepository(val definitionsService: UrbanDictionaryDefinitionsService) {

    fun getDefinitions(term: String): Single<UrbanDictionaryDefinitions> {
        return definitionsService.getDefinitions(term)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}