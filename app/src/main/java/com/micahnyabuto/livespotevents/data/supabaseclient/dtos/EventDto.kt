package com.micahnyabuto.livespotevents.data.supabaseclient.dtos

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String? =null,
    val title: String,

    @SerializedName("event_date")
    val eventDate: String,
    @SerializedName("event_time")
    val eventTime: String,
    @SerializedName("event_location")
    val location: String,
    @SerializedName("event_description")
    val description: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("created_at")
    val createdAt: String? = null

)