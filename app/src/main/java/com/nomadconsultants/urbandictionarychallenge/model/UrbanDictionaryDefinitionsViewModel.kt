package com.nomadconsultants.urbandictionarychallenge.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadconsultants.urbandictionarychallenge.repository.UrbanDictionaryDefinitionsRepository
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class UrbanDictionaryDefinitionsViewModel(val definitionsRepository: UrbanDictionaryDefinitionsRepository) : ViewModel() {

    var definitionsList: MutableLiveData<UrbanDictionaryDefinitions> = MutableLiveData<UrbanDictionaryDefinitions>()
    val definitionsHashMap = mutableMapOf<String, UrbanDictionaryDefinitions>()
    lateinit var disposable: Disposable

    fun getDefinitions(term: String) {
        // did we get it already?
        if (definitionsHashMap.contains(term)) {
            definitionsList.postValue(definitionsHashMap[term])
        } else {
            disposable = definitionsRepository.getDefinitions(term)
                .subscribeWith(object: DisposableObserver<UrbanDictionaryDefinitions>() {

                override fun onError(e: Throwable) {
                    Log.d("UrbanDictionaryDefinitionsViewModel", "onError");
                    definitionsList.postValue(createDummyDefinitions())
                }

                override fun onNext(data: UrbanDictionaryDefinitions) {
                    Log.d("UrbanDictionaryDefinitionsViewModel", "onNext");
                    if (data.definitions.isNotEmpty()) {
                        definitionsList.postValue(data)
                        definitionsHashMap.put(term, data)
                    } else {
                        definitionsList.postValue(createDummyDefinitions())
                    }
                }

                override fun onComplete() {
                    Log.d("UrbanDictionaryDefinitionsViewModel", "onComplete");
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

    override fun onCleared() {
        super.onCleared()
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}