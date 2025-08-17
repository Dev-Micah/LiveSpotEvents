package com.micahnyabuto.livespotevents.di

import com.micahnyabuto.livespotevents.core.data.DummyEventsRepository
import com.micahnyabuto.livespotevents.features.viewmodel.DummyEventViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        DummyEventsRepository(get())
    }

    viewModel {
        DummyEventViewModel(get())

    }
}