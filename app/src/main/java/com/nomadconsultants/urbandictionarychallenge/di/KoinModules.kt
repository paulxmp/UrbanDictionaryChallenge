package com.nomadconsultants.urbandictionarychallenge.di

import com.nomadconsultants.urbandictionarychallenge.model.UrbanDictionaryDefinitionsViewModel
import com.nomadconsultants.urbandictionarychallenge.repository.UrbanDictionaryDefinitionsRepository
import com.nomadconsultants.urbandictionarychallenge.service.UrbanDictionaryDefinitionsService
import com.nomadconsultants.urbandictionarychallenge.service.getDefinitionsService
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var appModule = module {
    // repository
    single<UrbanDictionaryDefinitionsRepository> { UrbanDictionaryDefinitionsRepository(get()) }
}

var serviceModule = module {
    // service
    single<UrbanDictionaryDefinitionsService> { getDefinitionsService() }
}

var viewModelModule = module {
    viewModel { UrbanDictionaryDefinitionsViewModel(get()) }
}