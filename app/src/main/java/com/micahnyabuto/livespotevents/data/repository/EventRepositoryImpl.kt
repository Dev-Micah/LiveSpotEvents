package com.micahnyabuto.livespotevents.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.micahnyabuto.livespotevents.data.supabaseclient.SupabaseClientInstance
import com.micahnyabuto.livespotevents.domain.model.Event
import com.micahnyabuto.livespotevents.domain.repository.EventRepository
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import io.ktor.http.ContentType
import java.io.File

class EventRepositoryImpl(private val client: SupabaseClientInstance) : EventRepository {

    override suspend fun createEvent(event: Event) {

        try {
            Log.d("EventsRepository", "Creating event: $event")
            client.client.from("events").insert(event)
            Log.d("EventsRepository", "Event created successfully")
        } catch (e: Exception) {
            Log.e("EventsRepository", "Error creating event", e)
            throw e
        }
    }

    override suspend fun uploadEventImage(
        context: Context,
        imageUri: Uri
    ): String? {
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

    override suspend fun getEvents(): List<Event>{
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