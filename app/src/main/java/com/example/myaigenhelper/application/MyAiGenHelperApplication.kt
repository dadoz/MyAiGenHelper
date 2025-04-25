package com.example.myaigenhelper.application

import android.app.Application
import com.application.example.myaigenhelper.BuildConfig.DEBUG
import timber.log.Timber

class MyAiGenHelperApplication() : Application() {
    init {
        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
