package com.map.phamthanhtung.vocubularybuilder.data

import com.map.phamthanhtung.vocubularybuilder.data.Definition

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)
