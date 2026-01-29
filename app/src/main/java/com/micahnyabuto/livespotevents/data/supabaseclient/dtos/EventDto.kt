package com.micahnyabuto.livespotevents.data.supabaseclient.dtos

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.gson.annotations.SerializedName
import com.micahnyabuto.livespotevents.domain.model.Event
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import io.ktor.http.ContentType
import kotlinx.serialization.Serializable
import java.io.File

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


suspend fun createEvent(event: Event) {
    try {
        Log.d("EventsRepository", "Creating event: $event")
        client.client.from("events").insert(event)
        Log.d("EventsRepository", "Event created successfully")
    } catch (e: Exception) {
        Log.e("EventsRepository", "Error creating event", e)
        throw e
    }
}


suspend fun uploadEventImage(context: Context, imageUri: Uri): String? {
    return try {
        val filename = "event_${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, filename)

        context.contentResolver.openInputStream(imageUri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val bucket = client.client.storage["event-images"]

        bucket.upload(


            path = filename,
            data = file.readBytes(),
            options = {
                upsert = true
                contentType = ContentType.Image.JPEG
            }
        )

        val publicUrl = bucket.publicUrl(filename)
        Log.d("EventsRepository", "Public URL: $publicUrl")

        file.delete()

        publicUrl
    } catch (e: Exception) {
        Log.e("EventsRepository", "Error uploading image", e)
        null
    }
}

suspend fun getEvents(): List<Event> {
    return try {
        Log.d("EventsRepository", "Fetching events from database...")
        val events = client.client.from("events").select().decodeList<Event>()
        Log.d("EventsRepository", "Fetched ${events.size} events: $events")
        events
    } catch (e: Exception) {
        Log.e("EventsRepository", "Error fetching events", e)
        throw e
    }
}
