package com.micahnyabuto.livespotevents.di

import com.micahnyabuto.livespotevents.core.domain.EventsRepository
import com.micahnyabuto.livespotevents.features.create.EventsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        EventsRepository(
            client = get()
        )
    }

    viewModel {
        EventsViewModel(get())

    }
}