package com.map.phamthanhtung.vocubularybuilder.repository

import com.map.phamthanhtung.vocubularybuilder.data.ApiResponseHandler
import com.map.phamthanhtung.vocubularybuilder.data.DictionaryResponse

class VocabularyRepository {
    private val apiService = ApiResponseHandler.apiService

    suspend fun fetchVocabulary(word: String): List<DictionaryResponse> {
        return apiService.getVocabulary(word)
    }

}
