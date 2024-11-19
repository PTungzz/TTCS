package com.map.phamthanhtung.vocubularybuilder.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiResponseHandler {
    private const val BASE_URL = "https://api.dictionaryapi.dev/"

    val apiService: DictionaryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }
}
