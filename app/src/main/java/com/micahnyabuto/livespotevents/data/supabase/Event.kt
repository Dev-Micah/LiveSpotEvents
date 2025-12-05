package com.micahnyabuto.livespotevents.data.supabase

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: Int? = null,
    val title: String,
    val event_date: String,
    val event_time: String,
    val location: String,
    val description: String?,
    val image_url: String?,
    val created_at: String? = null
)
