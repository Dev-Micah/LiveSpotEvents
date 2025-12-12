package com.micahnyabuto.livespotevents.domain.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Event(
    val id: String? =null,
    val title: String,
    val event_date: String,
    val event_time: String,
    val location: String,
    val description: String?,
    val image_url: String?,
    val created_at: String? = null

)