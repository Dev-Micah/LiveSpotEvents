package com.micahnyabuto.livespotevents.features.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.livespotevents.core.data.supabase.Event
import com.micahnyabuto.livespotevents.core.domain.EventsRepository
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
                _events.value = repository.getEvents()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createEvent(
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
                val imageUrl = imageUri?.let { repository.uploadEventImage(it) }
                val event = Event(
                    title = title,
                    event_date = date,
                    event_time = time,
                    location = location,
                    description = description,
                    image_url = imageUrl
                )
                repository.createEvent(event)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }


}
