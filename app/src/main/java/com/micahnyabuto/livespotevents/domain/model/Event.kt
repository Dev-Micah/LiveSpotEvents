package com.micahnyabuto.livespotevents.domain.model

data class Event(
    val id: String? =null,
    val title: String,
    val eventDate: String,
    val eventTime: String,
    val location: String,
    val description: String?,
    val imageUrl: String?,
    val createdAt: String? = null

)