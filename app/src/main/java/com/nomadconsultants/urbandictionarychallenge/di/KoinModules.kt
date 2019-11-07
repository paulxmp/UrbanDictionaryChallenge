package com.nomadconsultants.urbandictionarychallenge.di

import com.nomadconsultants.urbandictionarychallenge.model.UrbanDictionaryDefinitionsViewModel
import com.nomadconsultants.urbandictionarychallenge.service.UrbanDictionaryDefinitionsService
import com.nomadconsultants.urbandictionarychallenge.service.getDefinitionsService
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var serviceModule = module {
    // service
    single<UrbanDictionaryDefinitionsService> { getDefinitionsService() }
}

var viewModelModule = module {
    viewModel { UrbanDictionaryDefinitionsViewModel(get()) }
}