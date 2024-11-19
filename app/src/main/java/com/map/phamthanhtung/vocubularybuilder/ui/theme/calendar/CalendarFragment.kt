package com.map.phamthanhtung.vocubularybuilder.ui.theme.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.map.phamthanhtung.vocubularybuilder.R
import com.map.phamthanhtung.vocubularybuilder.adapter.CalendarAdapter
import com.map.phamthanhtung.vocubularybuilder.model.CalendarViewModel
import com.map.phamthanhtung.vocubularybuilder.ui.theme.vocabulary.AddWordFragment

class CalendarFragment : Fragment() {

    private lateinit var selectedDateTextView: TextView
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var calendarAdapter: CalendarAdapter
    private val calendarViewModel: CalendarViewModel by activityViewModels()
    private var selectedDate: String? = null // Variable to store the selected date

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        selectedDateTextView = view.findViewById(R.id.selected_date_textview)
        eventsRecyclerView = view.findViewById(R.id.recyclerView)

        // Set up RecyclerView with Adapter
        calendarAdapter = CalendarAdapter(
            mutableListOf(),
            { event ->
                Log.d("CalendarFragment", "Favorite clicked for event: ${event.description}")
                calendarAdapter.toggleFavorite(event)
            },
            { event ->
                Log.d("CalendarFragment", "Delete clicked for event: ${event.description}")
                calendarAdapter.removeEvent(event)
            },
            {
                Log.d("CalendarFragment", "Add button clicked")
                // Check if a date is selected
                if (selectedDate != null) {
                    // Send the selected date to AddWordFragment
                    val addWordFragment = AddWordFragment()
                    val bundle = Bundle()
                    bundle.putString("selectedDate", selectedDate) // Pass the selected date
                    addWordFragment.arguments = bundle

                    // Perform fragment transaction
                    val fragmentTransaction = parentFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.nav_host_fragment, addWordFragment) // Replace the fragment
                    fragmentTransaction.addToBackStack(null) // Add to back stack if needed
                    fragmentTransaction.commit()
                } else {
                    Log.d("CalendarFragment", "No date selected")
                }
            }
        )

        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.adapter = calendarAdapter

        // Set up CalendarView
        val calendarView = view.findViewById<android.widget.CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year" // Update the selected date
            selectedDateTextView.text = "Selected Date: $selectedDate" // Display the selected date

            // Filter events based on the selected date from calendarViewModel
            val filteredEvents = calendarViewModel.eventList.value?.filter {
                it.date == selectedDate
            }

            // Log the filtered event information
            Log.d("CalendarFragment", "Filtered events: ${filteredEvents?.size}")

            // Update the existing adapter with the filtered events (don't recreate the adapter)
            calendarAdapter.submitList(filteredEvents ?: mutableListOf())
        }

        return view
    }
}


