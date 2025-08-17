package com.micahnyabuto.livespotevents.core.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Event(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val description: String,
    val imageName: String
)

