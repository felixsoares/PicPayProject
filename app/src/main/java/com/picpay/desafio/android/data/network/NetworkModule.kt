package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.data.util.Util
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single {
        OkHttpClient
            .Builder()
            .cache(get<Cache>())
            .addInterceptor(get<CacheInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        val cacheSize = (5 * 1024 * 1024).toLong()
        Cache(androidContext().cacheDir, cacheSize)
    }

    single {
        CacheInterceptor(androidContext())
    }

    single<GsonConverterFactory> {
        GsonConverterFactory
            .create()
    }

    single<PicPayService> {
        get<Retrofit>().create(PicPayService::class.java)
    }
}