package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.network.PicPayService
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get<PicPayService>())
    }
}