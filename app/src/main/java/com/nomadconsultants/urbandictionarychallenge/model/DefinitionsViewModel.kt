package com.nomadconsultants.urbandictionarychallenge.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadconsultants.urbandictionarychallenge.service.DefinitionsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefinitionsViewModel(val definitionsService: DefinitionsService) : ViewModel() {
    val TAG = DefinitionsViewModel::class.java.simpleName
    var definitionsList: MutableLiveData<Definitions> = MutableLiveData<Definitions>()

    fun getDefinitions(term: String) {
        definitionsService.getDefinitions(term).enqueue(object : Callback<Definitions> {
            override fun onFailure(call: Call<Definitions>, t: Throwable) {
                Log.d(TAG, "onFailure")
            }

            override fun onResponse(call: Call<Definitions>, response: Response<Definitions>) {
                Log.d(TAG, "onResponse")
                if (response.isSuccessful) {
                    val responseDefinitions = response.body()
                    if (responseDefinitions != null) {
                        if (responseDefinitions.definitions.isNotEmpty()) {
                            definitionsList.postValue(response.body())
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

    fun createDummyDefinitions(): Definitions {
        val dummyDefinitionList = listOf(Definition(
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
        return Definitions(definitions = dummyDefinitionList)
    }
}