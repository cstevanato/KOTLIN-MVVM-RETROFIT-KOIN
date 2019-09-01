package br.com.architerure.stv.app

import android.app.Application
import br.com.architerure.stv.app.modules.appModule
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            this@MoviesApplication
            modules(listOf(appModule))}
    }
}