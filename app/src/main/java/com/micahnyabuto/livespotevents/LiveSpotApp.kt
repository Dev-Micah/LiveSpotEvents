package com.micahnyabuto.livespotevents

import android.app.Application
import com.micahnyabuto.livespotevents.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LiveSpotApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {

            androidContext(this@LiveSpotApp)
            modules(appModule)
        }

    }


}