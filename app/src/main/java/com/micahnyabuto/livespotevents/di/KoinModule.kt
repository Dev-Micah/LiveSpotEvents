package com.micahnyabuto.livespotevents.di

import com.micahnyabuto.livespotevents.data.repository.EventRepositoryImpl
import com.micahnyabuto.livespotevents.data.supabaseclient.SupabaseClientInstance
import com.micahnyabuto.livespotevents.domain.repository.EventRepository
import com.micahnyabuto.livespotevents.ui.screens.events.EventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        SupabaseClientInstance
    }
    single<EventRepositoryImpl> { EventRepositoryImpl(get()) }
    single<EventRepository> { get()}

    viewModel {
        EventsViewModel(get())
    }
}
