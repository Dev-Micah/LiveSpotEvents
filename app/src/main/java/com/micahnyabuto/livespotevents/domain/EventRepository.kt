package com.micahnyabuto.livespotevents.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import com.micahnyabuto.livespotevents.data.supabase.Event
import com.micahnyabuto.livespotevents.data.supabase.SupabaseClientInstance
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import io.ktor.http.*
import java.io.File

class EventsRepository(
    private val client: SupabaseClientInstance
) {
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
            val file = File(context.cacheDir, "event_${System.currentTimeMillis()}.jpg")

            context.contentResolver.openInputStream(imageUri)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            val bucket = client.client.storage["event-images"]
            val filePath = "${System.currentTimeMillis()}.jpg"

            bucket.upload(
                path = filePath,
                data = file.readBytes(),
                options = {
                    upsert = true
                    contentType = ContentType.Image.JPEG
                }
            )

            val publicUrl = bucket.publicUrl(filePath)

            file.delete()

            publicUrl
        } catch (e: Exception) {
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
}