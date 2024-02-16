package com.example.ftblappfinal.di

import com.example.ftblappfinal.data.remote.LiveScoreAPI
import com.example.ftblappfinal.util.BASE_URL
import com.example.ftblappfinal.util.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun okHttp(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor().apply { level= HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RequestInterceptor())
            .build()

    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun LiveScoreAPI(retrofit: Retrofit): LiveScoreAPI {
        return retrofit.create(LiveScoreAPI::class.java)
    }
}