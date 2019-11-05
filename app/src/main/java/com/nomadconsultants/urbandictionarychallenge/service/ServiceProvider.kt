package com.nomadconsultants.urbandictionarychallenge.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nomadconsultants.urbandictionarychallenge.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun getMoshiConverterFactory() = MoshiConverterFactory.create(
    Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build())

fun getLoggingInterceptor() = HttpLoggingInterceptor()
    .apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

fun getOkHttpClient(vararg interceptors: Interceptor): OkHttpClient {
    val client = OkHttpClient.Builder()

    client.connectTimeout(30, TimeUnit.SECONDS)
    client.writeTimeout(30, TimeUnit.SECONDS)
    client.readTimeout(30, TimeUnit.SECONDS)

    interceptors.forEach {
        client.interceptors().add(it)
    }
    return client.build()
}

fun getDefinitionsService(): DefinitionsService {
    val headerInterceptor = Interceptor { chain ->
        chain.proceed(chain
            .request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("x-rapidapi-host", "mashape-community-urban-dictionary.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "3528bc4e7emsha0c76cdf3541cc6p1edfe6jsna6a4a1d8d0c4")
            .build())
    }

    return Retrofit.Builder()
        .baseUrl(BuildConfig.URBAN_DICTIONATRY_API_URL)
        .addConverterFactory(getMoshiConverterFactory())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(getOkHttpClient(getLoggingInterceptor(), headerInterceptor))
        .build()
        .create(DefinitionsService::class.java)
}
