package com.interview.kingpower

import android.app.Application
import com.interview.kingpower.module.repoModules
import com.interview.kingpower.module.serviceModules
import com.interview.kingpower.module.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(listOf(repoModules, serviceModules, viewModelModules))
        }
    }
}