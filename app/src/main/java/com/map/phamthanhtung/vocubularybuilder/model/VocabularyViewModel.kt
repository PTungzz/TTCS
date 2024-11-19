package com.map.phamthanhtung.vocubularybuilder.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.map.phamthanhtung.vocubularybuilder.data.DictionaryResponse
import com.map.phamthanhtung.vocubularybuilder.repository.VocabularyRepository
import kotlinx.coroutines.launch

class VocabularyViewModel : ViewModel() {
    private val repository = VocabularyRepository()

    private val _vocabularyResponse = MutableLiveData<List<DictionaryResponse>>()
    val vocabularyResponse: LiveData<List<DictionaryResponse>> get() = _vocabularyResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchVocabulary(word: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchVocabulary(word)
                _vocabularyResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
