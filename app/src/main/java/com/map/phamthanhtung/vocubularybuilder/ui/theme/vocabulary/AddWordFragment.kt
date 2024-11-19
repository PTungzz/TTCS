package com.map.phamthanhtung.vocubularybuilder.ui.theme.vocabulary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.map.phamthanhtung.vocubularybuilder.R
import com.map.phamthanhtung.vocubularybuilder.data.Event
import com.map.phamthanhtung.vocubularybuilder.model.CalendarViewModel

class AddWordFragment : Fragment() {

    private lateinit var wordEditText: EditText
    private lateinit var meaningEditText: EditText
    private lateinit var saveButton: Button
    private val calendarViewModel: CalendarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_word, container, false)

        // Lấy các tham chiếu đến các view
        wordEditText = view.findViewById(R.id.word_input)
        meaningEditText = view.findViewById(R.id.definition_input)
        saveButton = view.findViewById(R.id.save_word_button)

        // Cài đặt sự kiện click cho nút Save
        saveButton.setOnClickListener {
            val word = wordEditText.text.toString()
            val meaning = meaningEditText.text.toString()

            // Lấy ngày đã chọn từ arguments
            val selectedDate = arguments?.getString("selectedDate") ?: ""

            Log.d("AddWordFragment", "Save button clicked. Word: $word, Meaning: $meaning, Selected Date: $selectedDate")

            if (word.isNotEmpty() && meaning.isNotEmpty()) {
                // Tạo một đối tượng Event
                val newEvent = Event(date = selectedDate, description = "$word: $meaning")

                // Log thông tin sự kiện trước khi gửi vào ViewModel
                Log.d("AddWordFragment", "Creating new event: $newEvent")

                // Gửi sự kiện mới vào ViewModel
                calendarViewModel.addEvent(newEvent)

                // Log sau khi gửi sự kiện vào ViewModel
                Log.d("AddWordFragment", "Event added to ViewModel")

                // Quay lại fragment trước đó
                requireActivity().supportFragmentManager.popBackStack()
                Log.d("AddWordFragment", "Returning to previous fragment.")
            } else {
                // Xử lý trường hợp không có dữ liệu nhập vào
                Log.d("AddWordFragment", "Invalid input. Word or meaning is empty.")
                Toast.makeText(requireContext(), "Please enter both word and meaning", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
