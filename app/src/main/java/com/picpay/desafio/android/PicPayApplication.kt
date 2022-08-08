package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.data.network.networkModule
import com.picpay.desafio.android.data.repository.repositoryModule
import com.picpay.desafio.android.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PicPayApplication)
            modules(networkModule, repositoryModule, uiModule)
        }
    }

}