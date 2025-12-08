package com.micahnyabuto.livespotevents.di

import com.micahnyabuto.livespotevents.data.supabase.SupabaseClientInstance
import com.micahnyabuto.livespotevents.domain.EventsRepository
import com.micahnyabuto.livespotevents.ui.screens.events.EventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        SupabaseClientInstance
    }

    single {
        EventsRepository(
            client = get()
        )
    }


    viewModel {
        EventsViewModel(get())
    }
}
