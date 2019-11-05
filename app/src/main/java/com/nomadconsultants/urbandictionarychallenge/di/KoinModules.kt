package com.nomadconsultants.urbandictionarychallenge.di

import com.nomadconsultants.urbandictionarychallenge.model.DefinitionsViewModel
import com.nomadconsultants.urbandictionarychallenge.service.DefinitionsService
import com.nomadconsultants.urbandictionarychallenge.service.getDefinitionsService
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var appModule = module {
    // service
    single<DefinitionsService> { getDefinitionsService() }
}

var viewModelModule = module {
    viewModel { DefinitionsViewModel(get()) }
}