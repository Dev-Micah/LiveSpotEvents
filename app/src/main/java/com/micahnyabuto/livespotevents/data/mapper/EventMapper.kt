package com.micahnyabuto.livespotevents.data.mapper

import com.micahnyabuto.livespotevents.data.supabaseclient.dtos.EventDto
import com.micahnyabuto.livespotevents.domain.model.Event

fun EventDto.toDomain(): Event{
    return Event(
        id = id,
        title = title,
        eventDate = eventDate,
        eventTime = eventTime,
        location = location,
        description = description,
        imageUrl = imageUrl,
        createdAt = createdAt
    )
}