package com.micahnyabuto.livespotevents.ui.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.livespotevents.data.supabase.Event
import com.micahnyabuto.livespotevents.domain.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GetEventsViewModel(
    private val eventsRepository: EventsRepository
): ViewModel(){

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()
    fun loadEvents(){
        viewModelScope.launch {
            val result = eventsRepository.getEvents()
            _events.value = result
        }
    }
}