package com.erfolgi.omdb.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    const val apiKey="49aee61b";
    const val baseURL = "https://www.omdbapi.com/"

    const val imgURL = "https://img.omdbapi.com/"

    //private const val apiURL = "$baseURL/api/v1/"
    fun getClient() : Retrofit {
        val logging = HttpLoggingInterceptor()

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}