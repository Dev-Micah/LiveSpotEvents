package com.micahnyabuto.livespotevents.ui.screens.events

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.micahnyabuto.livespotevents.data.supabase.Event
import com.micahnyabuto.livespotevents.domain.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EventsViewModel(private val repository: EventsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    fun loadEvents() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val eventsList = repository.getEvents()
                _uiState.update {
                    it.copy(isLoading = false, events = eventsList)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = e.message)
                }
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
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isCreating = true) }
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
                _uiState.update { it.copy(isCreating = false, createError = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isCreating = false, createError = e.message) }
            }
        }
    }
    fun resetCreateEventState() {
        _uiState.update { it.copy(isCreating = false, createError = null) }
    }
}

data class EventsUiState(
    val isLoading: Boolean = false,
    val events: List<Event> = emptyList(),
    val error: String? = null,
    val isCreating: Boolean = false,
    val createError: String? = null
)
