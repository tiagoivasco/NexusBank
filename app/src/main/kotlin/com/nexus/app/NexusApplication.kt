package com.nexus.app

import android.app.Application
import com.nexus.app.di.appModule
import com.nexus.core.network.di.networkModule
import com.nexus.feature.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NexusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@NexusApplication)
            modules(
                appModule,    // flavor-specific: FlavorConfig + NetworkConfig
                networkModule, // OkHttp, Retrofit
                homeModule    // feature: home
            )
        }
    }
}
