package com.micahnyabuto.livespotevents.core.workermanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.micahnyabuto.livespotevents.domain.model.Event
import com.micahnyabuto.livespotevents.domain.repository.EventRepository
import kotlinx.serialization.json.Json

class CreateEventWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val eventRepository: EventRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val eventJson = inputData.getString("EVENT_DATA") ?: return Result.failure()

        return try {
            val eventRequest = Json.decodeFromString<Event>(eventJson)
            eventRepository.createEventInSupabase(eventRequest)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}