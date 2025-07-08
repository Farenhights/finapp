package com.finapp.application

import android.app.Application
import com.finapp.di.modules.networkModule
import com.finapp.di.modules.repositoryModule
import com.finapp.di.modules.useCaseModule
import com.finapp.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(listOf(viewModelModule, useCaseModule, repositoryModule, networkModule))
        }
    }
}