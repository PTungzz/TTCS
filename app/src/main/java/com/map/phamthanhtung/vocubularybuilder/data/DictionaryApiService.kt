package com.map.phamthanhtung.vocubularybuilder.data

import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("api/v2/entries/en/{word}")
    suspend fun getVocabulary(@Path("word") word: String): List<DictionaryResponse>
}