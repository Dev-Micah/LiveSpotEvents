package com.micahnyabuto.livespotevents.data.supabaseclient

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClientInstance {
    val client = createSupabaseClient(
        supabaseUrl = "",
        supabaseKey = ""
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}


