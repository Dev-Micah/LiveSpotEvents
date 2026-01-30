package com.micahnyabuto.livespotevents.domain.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.micahnyabuto.livespotevents.domain.model.Event
import com.micahnyabuto.livespotevents.data.supabaseclient.SupabaseClientInstance
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import io.ktor.http.*
import java.io.File

interface EventRepository{
    suspend fun createEvent(event: Event)
    suspend fun uploadEventImage(context: Context, imageUri: Uri): String?
    suspend fun getEvents(): List<Event>
}