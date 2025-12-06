package com.micahnyabuto.livespotevents.ui.screens.events

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.livespotevents.data.supabase.Event
import com.micahnyabuto.livespotevents.domain.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventsViewModel(private val repository: EventsRepository) : ViewModel() {

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    fun loadEvents() {
        viewModelScope.launch {
            try {
                Log.d("EventsViewModel", "Loading events...")
                val eventsList = repository.getEvents()
                Log.d("EventsViewModel", "Loaded ${eventsList.size} events: $eventsList")
                _events.value = eventsList
            } catch (e: Exception) {
                Log.e("EventsViewModel", "Error loading events", e)
                e.printStackTrace()
            }
        }
    }

    fun createEvent(
        context: Context,
        title: String,
        date: String,
        time: String,
        location: String,
        description: String,
        imageUri: Uri?,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val imageUrl = imageUri?.let { repository.uploadEventImage(context, it) }
                val event = Event(
                    title = title,
                    event_date = date,
                    event_time = time,
                    location = location,
                    description = description,
                    image_url = imageUrl
                )
                repository.createEvent(event)
                loadEvents()
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }


}

data class EventsUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String? = null,
)
