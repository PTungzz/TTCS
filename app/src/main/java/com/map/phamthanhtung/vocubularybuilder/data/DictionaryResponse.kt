package com.map.phamthanhtung.vocubularybuilder.data

data class DictionaryResponse(
    val word: String,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>
)
