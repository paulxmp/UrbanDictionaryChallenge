package com.nomadconsultants.urbandictionarychallenge.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadconsultants.urbandictionarychallenge.service.UrbanDictionaryDefinitionsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UrbanDictionaryDefinitionsViewModel(val definitionsService: UrbanDictionaryDefinitionsService) : ViewModel() {

    var definitionsList: MutableLiveData<UrbanDictionaryDefinitions> = MutableLiveData<UrbanDictionaryDefinitions>()
    val definitionsHashMap = mutableMapOf<String, UrbanDictionaryDefinitions>()

    fun getDefinitions(term: String) {
        // did we get it already?
        if (definitionsHashMap.contains(term)) {
            definitionsList.postValue(definitionsHashMap[term])
        } else {
            definitionsService.getDefinitions(term).enqueue(object : Callback<UrbanDictionaryDefinitions> {
                override fun onFailure(call: Call<UrbanDictionaryDefinitions>, t: Throwable) {
                }

                override fun onResponse(call: Call<UrbanDictionaryDefinitions>, response: Response<UrbanDictionaryDefinitions>) {
                    if (response.isSuccessful) {
                        val responseDefinitions = response.body()
                        responseDefinitions?.let {
                            if (it.definitions.isNotEmpty()) {
                                definitionsList.postValue(response.body())
                                definitionsHashMap.put(term, it)
                            } else {
                                definitionsList.postValue(createDummyDefinitions())
                            }
                        }
                    } else {
                        definitionsList.postValue(createDummyDefinitions())
                    }
                }
            })
        }
    }

    fun createDummyDefinitions(): UrbanDictionaryDefinitions {
        val dummyDefinitionList = listOf(UrbanDictionaryDefinition(
            definition = "No definition found.",
            permalink = "No author found.",
            thumbsUp = 0,
            soundUrls = listOf(""),
            author = "No author found.",
            word = "No word found.",
            defid = 0,
            currentVote = "No current vote found.",
            writtenOn = "No written on found.",
            example = "No example found.",
            thumbsDown = 0
        ))
        return UrbanDictionaryDefinitions(definitions = dummyDefinitionList)
    }

    fun getPreviousTerms() = definitionsHashMap.keys
}