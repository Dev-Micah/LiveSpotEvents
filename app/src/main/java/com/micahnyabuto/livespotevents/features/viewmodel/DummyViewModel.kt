package com.micahnyabuto.livespotevents.features.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.livespotevents.core.data.DummyEventsRepository
import com.micahnyabuto.livespotevents.core.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DummyEventViewModel(private val repository: DummyEventsRepository) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    fun fetchEvents() {
        viewModelScope.launch {
            val loadedEvents = repository.loadEvents()
            _events.value = loadedEvents
        }
    }
}
