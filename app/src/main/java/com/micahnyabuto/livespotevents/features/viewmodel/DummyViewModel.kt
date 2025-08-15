package com.micahnyabuto.livespotevents.features.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.micahnyabuto.livespotevents.core.model.Event
import com.micahnyabuto.livespotevents.core.model.loadEventsFromAssets

class DummyEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun loadEvents(context: Context) {
        val loadedEvents = eventRepository.loadEventsFromAssets(context)
        _events.value = loadedEvents
    }
}
