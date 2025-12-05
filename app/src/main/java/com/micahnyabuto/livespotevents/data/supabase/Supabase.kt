package com.micahnyabuto.livespotevents.data.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClientInstance {
    val client = createSupabaseClient(
        supabaseUrl = "https://reimtwigdzdspzshwljc.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InJlaW10d2lnZHpkc3B6c2h3bGpjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTU2MzY2NTAsImV4cCI6MjA3MTIxMjY1MH0.-8xXcy2yIZPYojHhfGZw57tmDzge3GSsYHrpPkGb1Qc"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
}


