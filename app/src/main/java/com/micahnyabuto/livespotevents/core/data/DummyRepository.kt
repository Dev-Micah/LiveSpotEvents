package com.micahnyabuto.livespotevents.core.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.micahnyabuto.livespotevents.core.model.Event

class DummyEventsRepository(private val context: Context) {

    fun loadEvents(): List<Event> {
        fun loadEvents(): List<Event> {
            val jsonString = context.assets.open("events.json")
                .bufferedReader().use { it.readText() }

            val gson = Gson()
            val listType = object : TypeToken<List<Event>>() {}.type

            return gson.fromJson(jsonString, listType)
        }
        return loadEvents()
    }
}