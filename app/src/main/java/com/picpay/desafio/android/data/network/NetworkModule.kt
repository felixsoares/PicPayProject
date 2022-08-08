package com.picpay.desafio.android.data.network

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/")
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .build()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory
            .create()
    }

    single<PicPayService> {
        get<Retrofit>().create(PicPayService::class.java)
    }
}