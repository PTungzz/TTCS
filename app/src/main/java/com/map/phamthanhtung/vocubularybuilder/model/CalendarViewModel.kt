package com.map.phamthanhtung.vocubularybuilder.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.map.phamthanhtung.vocubularybuilder.data.Event

class CalendarViewModel : ViewModel() {

    private val _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList


    fun addEvent(event: Event) {
        val updatedList = _eventList.value?.toMutableList() ?: mutableListOf()
        updatedList.add(event)
        _eventList.value = updatedList
    }
}
