package com.example.repositoriessearch.retrofit

import com.example.repositoriessearch.API_PASSWORD
import com.example.repositoriessearch.API_USERNAME
import com.example.repositoriessearch.BASE_URL
import com.example.repositoriessearch.network.SearchClient
import com.example.repositoriessearch.auth.AuthentificationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClientInstance {

    // Creating the custom Interceptor with Headers

    fun getRetrofitClient(): SearchClient {

        return Retrofit

            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY)) //Http Logging Interceptor
                    .addInterceptor(
                        AuthentificationInterceptor(API_USERNAME, API_PASSWORD)) //Basic auth through header
                    .build()
            )
            .build()
            .create()

    }
}

