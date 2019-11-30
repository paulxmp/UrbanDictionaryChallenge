package com.nomadconsultants.urbandictionarychallenge.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadconsultants.urbandictionarychallenge.repository.UrbanDictionaryDefinitionsRepository
import io.reactivex.observers.DisposableSingleObserver

class UrbanDictionaryDefinitionsViewModel(val definitionsRepository: UrbanDictionaryDefinitionsRepository) : ViewModel() {

    var definitionsList: MutableLiveData<UrbanDictionaryDefinitions> = MutableLiveData<UrbanDictionaryDefinitions>()
    val definitionsHashMap = mutableMapOf<String, UrbanDictionaryDefinitions>()

    fun getDefinitions(term: String) {
        // did we get it already?
        if (definitionsHashMap.contains(term)) {
            definitionsList.postValue(definitionsHashMap[term])
        } else {
            definitionsRepository.getDefinitions(term)
                .subscribe(object: DisposableSingleObserver<UrbanDictionaryDefinitions>() {

                override fun onError(e: Throwable) {
                    Log.d("UrbanDictionaryDefinitionsViewModel", "onError");
                    definitionsList.postValue(createDummyDefinitions())
                }

                override fun onSuccess(data: UrbanDictionaryDefinitions) {
                    Log.d("UrbanDictionaryDefinitionsViewModel", "onNext");
                    if (data.definitions.isNotEmpty()) {
                        definitionsList.postValue(data)
                        definitionsHashMap.put(term, data)
                    } else {
                        definitionsList.postValue(createDummyDefinitions())
                    }
                }

            })

        }
    }

    fun getPreviousTerms() = definitionsHashMap.keys

    fun createDummyDefinitions(): UrbanDictionaryDefinitions {
        val dummyDefinitionList = listOf(
            UrbanDictionaryDefinition(
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
            )
        )
        return UrbanDictionaryDefinitions(definitions = dummyDefinitionList)
    }

}