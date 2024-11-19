package com.map.phamthanhtung.vocubularybuilder.ui.theme.vocabulary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.map.phamthanhtung.vocubularybuilder.model.VocabularyViewModel
import com.map.phamthanhtung.vocubularybuilder.R

class VocabularyFragment : Fragment() {
    private val viewModel: VocabularyViewModel by viewModels()

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var wordTextView: TextView
    private lateinit var definitionTextView: TextView
    private lateinit var partOfSpeechTextView: TextView
    private lateinit var phoneticTextView: TextView // Declare phoneticTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vocabulary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find views by findViewById
        searchEditText = view.findViewById(R.id.searchEditText)
        searchButton = view.findViewById(R.id.searchButton)
        wordTextView = view.findViewById(R.id.wordTextView)
        definitionTextView = view.findViewById(R.id.definitionTextView)
        partOfSpeechTextView = view.findViewById(R.id.partOfSpeechTextView)
        phoneticTextView = view.findViewById(R.id.phoneticTextView) // Initialize phoneticTextView

        // Handle search button click
        searchButton.setOnClickListener {
            val word = searchEditText.text.toString()
            if (word.isNotEmpty()) {
                viewModel.fetchVocabulary(word)
            } else {
                Toast.makeText(requireContext(), "Please enter a word", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe data from ViewModel
        viewModel.vocabularyResponse.observe(viewLifecycleOwner) { response ->
            if (response.isNotEmpty()) {
                val word = response[0]
                wordTextView.text = word.word

                // Display phonetics
                val phonetics = word.phonetics.joinToString(", ") { it.text.toString() }
                phoneticTextView.text = "Phonetics: $phonetics"

                // Display part of speech and meanings separately
                val meaningsText = buildString {
                    word.meanings.forEach { meaning ->
                        append("Part of Speech: ${meaning.partOfSpeech}\n")
                        meaning.definitions.forEach { definition ->
                            append("- ${definition.definition}\n")
                        }
                    }
                }
                definitionTextView.text = meaningsText
            }
        }

        // Observe errors
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), "Error: $error", Toast.LENGTH_SHORT).show()
        }
    }
}
