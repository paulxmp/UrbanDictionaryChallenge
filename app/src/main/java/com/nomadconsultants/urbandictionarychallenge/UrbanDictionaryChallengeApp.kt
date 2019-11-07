package com.nomadconsultants.urbandictionarychallenge

import androidx.multidex.MultiDexApplication
import com.nomadconsultants.urbandictionarychallenge.di.serviceModule
import com.nomadconsultants.urbandictionarychallenge.di.viewModelModule
import org.koin.android.ext.android.startKoin

class UrbanDictionaryChallengeApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
            serviceModule,
            viewModelModule
        ))
    }
}